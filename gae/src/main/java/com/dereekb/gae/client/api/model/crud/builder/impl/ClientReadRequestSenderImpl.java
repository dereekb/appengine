package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.services.ClientReadService;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestUrlImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientApiResponseErrorType;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.misc.parameters.impl.ParametersImpl;
import com.dereekb.gae.web.api.model.controller.ReadController;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see ReadController
 */
public class ClientReadRequestSenderImpl<T extends UniqueModel, O> extends AbstractSecuredClientModelRequestSender<ReadRequest, ReadResponse<T>>
        implements ClientReadRequestSender<T>, ClientReadService<T> {

	/**
	 * GET request to {@code /<type>} by default.
	 */
	public static final String DEFAULT_PATH_FORMAT = "/%s";

	private String type;
	private String pathFormat = DEFAULT_PATH_FORMAT;

	private boolean loadRelated = false;

	private Class<O> dtoType;
	private DirectionalConverter<O, T> dtoReader;
	private TypeModelKeyConverter keyTypeConverter;

	private String missingKeysErrorCode = MissingRequiredResourceException.API_RESPONSE_ERROR_CODE;

	public ClientReadRequestSenderImpl(String type,
	        Class<O> dtoType,
	        DirectionalConverter<O, T> dtoReader,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(requestSender);
		this.setType(type);
		this.setDtoType(dtoType);
		this.setDtoReader(dtoReader);
		this.setKeyTypeConverter(keyTypeConverter);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("type cannot be null.");
		}

		this.type = type;
	}

	public String getPathFormat() {
		return this.pathFormat;
	}

	public void setPathFormat(String pathFormat) {
		if (pathFormat == null) {
			throw new IllegalArgumentException("pathFormat cannot be null.");
		}

		this.pathFormat = pathFormat;
	}

	public boolean isLoadRelated() {
		return this.loadRelated;
	}

	public void setLoadRelated(boolean loadRelated) {
		this.loadRelated = loadRelated;
	}

	public Class<O> getDtoType() {
		return this.dtoType;
	}

	public void setDtoType(Class<O> dtoType) {
		if (dtoType == null) {
			throw new IllegalArgumentException("dtoType cannot be null.");
		}

		this.dtoType = dtoType;
	}

	public DirectionalConverter<O, T> getDtoReader() {
		return this.dtoReader;
	}

	public void setDtoReader(DirectionalConverter<O, T> dtoReader) {
		if (dtoReader == null) {
			throw new IllegalArgumentException("dtoReader cannot be null.");
		}

		this.dtoReader = dtoReader;
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		if (keyTypeConverter == null) {
			throw new IllegalArgumentException("keyTypeConverter cannot be null.");
		}

		this.keyTypeConverter = keyTypeConverter;
	}

	public String getMissingKeysErrorCode() {
		return this.missingKeysErrorCode;
	}

	public void setMissingKeysErrorCode(String missingKeysErrorCode) {
		if (missingKeysErrorCode == null) {
			throw new IllegalArgumentException("missingKeysErrorCode cannot be null.");
		}

		this.missingKeysErrorCode = missingKeysErrorCode;
	}

	// MARK: ClientReadService
	@Override
	public ReadResponse<T> read(ReadRequest request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<ReadResponse<T>> clientResponse = this.sendRequest(request,
		        this.getDefaultServiceSecurity());

		if (clientResponse.getSuccess() == false) {
			this.assertNoAtomicOperationError(clientResponse);
			throw new ClientRequestFailureException(clientResponse);
		}

		return clientResponse.getSerializedPrimaryData();
	}

	public void assertNoAtomicOperationError(ClientApiResponse clientResponse) throws ClientAtomicOperationException {
		ClientResponseError error = clientResponse.getError();

		if (error.getErrorType() == ClientApiResponseErrorType.OTHER_BAD_RESPONSE_ERROR) {
			List<ModelKey> missingKeys = this.serializeMissingKeys(clientResponse);

			if (missingKeys.isEmpty() == false) {
				throw new ClientAtomicOperationException(missingKeys, clientResponse);
			}
		}
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public ClientRequest buildClientRequest(ReadRequest request) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.GET);

		ReadRequestOptions options = request.getOptions();
		Iterable<ModelKey> modelKeys = request.getModelKeys();
		String keys = ModelKey.keysAsString(modelKeys, ",");

		ParametersImpl parameters = new ParametersImpl();

		parameters.addObjectParameter(ReadController.ATOMIC_PARAM, options.isAtomic());
		parameters.addObjectParameter(ReadController.LOAD_RELATED_PARAM, this.loadRelated);
		parameters.addObjectParameter(ReadController.KEYS_PARAM, keys);

		clientRequest.setParameters(parameters);
		return clientRequest;
	}

	@Override
	public ReadResponse<T> serializeResponseData(ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientReadResponseImpl(response);
	}

	private class ClientReadResponseImpl
	        implements ReadResponse<T> {

		private List<T> serializedModels;
		private List<ModelKey> serializedMissingKeys;

		private final ClientApiResponse response;

		public ClientReadResponseImpl(ClientApiResponse response) {
			this.response = response;
		}

		// MARK: ReadResponse
		@Override
		public Collection<T> getModels() {
			if (this.serializedModels == null) {
				ClientApiResponseData data = this.response.getPrimaryData();
				this.serializedModels = ClientReadRequestSenderImpl.this.serializeModels(data);
			}

			return this.serializedModels;
		}

		@Override
		public Collection<ModelKey> getFiltered() {
			return Collections.emptyList();
		}

		@Override
		public Collection<ModelKey> getUnavailable() {
			if (this.serializedMissingKeys == null) {
				this.serializedMissingKeys = ClientReadRequestSenderImpl.this.serializeMissingKeys(this.response);
			}

			return this.serializedMissingKeys;
		}

		@Override
		public String toString() {
			return "ClientReadResponseImpl [serializedModels=" + this.serializedModels + ", serializedMissingKeys="
			        + this.serializedMissingKeys + ", response=" + this.response + "]";
		}

	}

	// MARK: Internal
	public ClientRequestUrl makeRequestUrl() {
		String formattedPath = String.format(this.pathFormat, this.type);
		return new ClientRequestUrlImpl(formattedPath);
	}

	public List<T> serializeModels(ClientApiResponseData data) throws ClientResponseSerializationException {
		List<O> dtos = this.serializeModelDtos(data);
		return this.dtoReader.convert(dtos);
	}

	public List<O> serializeModelDtos(ClientApiResponseData data) throws ClientResponseSerializationException {
		ObjectMapper mapper = this.getObjectMapper();

		JsonNode jsonData = data.getDataJsonNode();

		List<O> dtoList = new ArrayList<O>();

		try {
			for (JsonNode result : jsonData) {
				O dto = mapper.treeToValue(result, this.dtoType);
				dtoList.add(dto);
			}
		} catch (JsonProcessingException e) {
			throw new ClientResponseSerializationException(e);
		}

		return dtoList;
	}

	public List<ModelKey> serializeMissingKeys(ClientApiResponse response) {
		List<ModelKey> unavailableObjectKeys = null;

		ClientResponseError error = response.getError();
		Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
		ClientResponseErrorInfo missingKeysInfo = errorInfoMap.get(this.missingKeysErrorCode);

		if (missingKeysInfo != null) {
			ObjectMapper mapper = this.getObjectMapper();
			JsonNode missingKeysData = missingKeysInfo.getErrorData();

			try {
				String[] keys = mapper.treeToValue(missingKeysData, String[].class);
				List<String> keysList = ListUtility.toList(keys);
				unavailableObjectKeys = this.keyTypeConverter.convertKeys(this.type, keysList);
			} catch (JsonProcessingException e) {
				throw new ClientResponseSerializationException(e);
			}
		} else {
			unavailableObjectKeys = Collections.emptyList();
		}

		return unavailableObjectKeys;
	}

	@Override
	public String toString() {
		return "ClientReadRequestSenderImpl [type=" + this.type + ", pathFormat=" + this.pathFormat + ", loadRelated="
		        + this.loadRelated + ", dtoType=" + this.dtoType + ", dtoReader=" + this.dtoReader
		        + ", keyTypeConverter=" + this.keyTypeConverter + ", missingKeysErrorCode=" + this.missingKeysErrorCode
		        + "]";
	}

}
