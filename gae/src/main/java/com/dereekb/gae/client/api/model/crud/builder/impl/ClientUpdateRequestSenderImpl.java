package com.dereekb.gae.client.api.model.crud.builder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.crud.services.ClientUpdateService;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.request.ClientRequest;
import com.dereekb.gae.client.api.service.request.ClientRequestMethod;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestDataImpl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.response.SimpleUpdateResponse;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.StringModelKeyConverter;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.web.api.model.request.ApiUpdateRequest;
import com.dereekb.gae.web.api.util.attribute.KeyedAttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedAttributeUpdateFailureApiResponseBuilder;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedAttributeUpdateFailureData;
import com.dereekb.gae.web.api.util.attribute.impl.KeyedAttributeUpdateFailureImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientUpdateRequestSender} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 */
public class ClientUpdateRequestSenderImpl<T extends UniqueModel, O> extends AbstractClientModelEditRequestSender<T, O, UpdateRequest<T>, SimpleUpdateResponse<T>>
        implements ClientUpdateRequestSender<T>, ClientUpdateService<T> {

	/**
	 * PUT request to {@code /<type>/update} by default.
	 */
	public static final String DEFAULT_PATH_FORMAT = "/%s/update";

	public ClientUpdateRequestSenderImpl(String type,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoReader,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(type, dtoType, dtoReader, keyTypeConverter, requestSender);
	}

	// MARK: Abstract
	@Override
	protected String getDefaultPathFormat() {
		return DEFAULT_PATH_FORMAT;
	}

	// MARK: Client Update Request Sender
	@Override
	public SimpleUpdateResponse<T> update(UpdateRequest<T> request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException {

		SerializedClientApiResponse<SimpleUpdateResponse<T>> clientResponse = this.sendRequest(request,
		        this.getDefaultServiceSecurity());
		this.assertSuccessfulResponse(clientResponse);
		return clientResponse.getSerializedPrimaryData();
	}

	// MARK: AbstractSecuredClientModelRequestSender
	@Override
	public ClientRequest buildClientRequest(UpdateRequest<T> request) {

		ClientRequestUrl url = this.makeRequestUrl();
		ClientRequestImpl clientRequest = new ClientRequestImpl(url, ClientRequestMethod.PUT);

		ObjectMapper mapper = this.getObjectMapper();

		ApiUpdateRequest<O> updateRequest = new ApiUpdateRequest<O>();

		UpdateRequestOptions options = request.getOptions();
		updateRequest.setRequestOptions(options);

		Collection<T> templates = request.getTemplates();
		BidirectionalConverter<T, O> converter = this.getDtoConverter();

		List<O> templateDtos = converter.convertTo(templates);
		updateRequest.setData(templateDtos);

		ClientRequestDataImpl requestData = ClientRequestDataImpl.make(mapper, updateRequest);
		clientRequest.setData(requestData);

		return clientRequest;
	}

	@Override
	public SimpleUpdateResponse<T> serializeResponseData(ClientApiResponse response)
	        throws ClientResponseSerializationException {
		return new ClientUpdateResponseImpl(response);
	}

	private class ClientUpdateResponseImpl extends AbstractClientServiceResponseImpl
	        implements SimpleUpdateResponse<T> {

		private List<T> serializedModels;
		private List<KeyedAttributeUpdateFailure> serializedFailures;

		public ClientUpdateResponseImpl(ClientApiResponse response) {
			super(response);
		}

		// MARK: UpdateResponse
		@Override
		public Collection<T> getModels() {
			if (this.serializedModels == null) {
				ClientApiResponseData data = this.response.getPrimaryData();
				this.serializedModels = ClientUpdateRequestSenderImpl.this.serializeModels(data);
			}

			return this.serializedModels;
		}

		@Override
		public Collection<ModelKey> getFailed() {
			Collection<KeyedAttributeUpdateFailure> failurePairs = this.getUpdateFailures();

			List<ModelKey> keys = ModelKey.readModelKeysFromKeyed(failurePairs);
			keys.addAll(super.getFailed());

			return keys;
		}

		@Override
		public Collection<ModelKey> getMissingKeys() {
			return super.getFailed();
		}

		@Override
		public Collection<KeyedAttributeUpdateFailure> getUpdateFailures() {
			if (this.serializedFailures == null) {
				this.serializedFailures = ClientUpdateRequestSenderImpl.this.serializeFailures(this.response);
			}

			return this.serializedFailures;
		}

	}

	public List<KeyedAttributeUpdateFailure> serializeFailures(ClientApiResponse response) {
		List<KeyedAttributeUpdateFailure> failures = null;

		ClientResponseError error = response.getError();
		Map<String, ClientResponseErrorInfo> errorInfoMap = error.getErrorInfoMap();
		ClientResponseErrorInfo failedPairsInfo = errorInfoMap
		        .get(KeyedAttributeUpdateFailureApiResponseBuilder.ERROR_CODE);

		if (failedPairsInfo != null) {
			failures = this.serializeFailuresFromErrorInfoData(failedPairsInfo);
		} else {
			failures = Collections.emptyList();
		}

		return failures;
	}

	public List<KeyedAttributeUpdateFailure> serializeFailuresFromErrorInfoData(ClientResponseErrorInfo keysErrorInfo) {
		JsonNode pairsData = keysErrorInfo.getErrorData();
		return this.serializeFailures(pairsData);
	}

	public List<KeyedAttributeUpdateFailure> serializeFailures(JsonNode keysArrayNode) {
		List<KeyedAttributeUpdateFailure> failures = new ArrayList<KeyedAttributeUpdateFailure>();

		TypeModelKeyConverter typeKeyConverter = this.getKeyTypeConverter();
		StringModelKeyConverter keyConverter = typeKeyConverter.getConverterForType(this.getType());
		ObjectMapper mapper = this.getObjectMapper();

		try {
			KeyedAttributeUpdateFailureData[] data = mapper.treeToValue(keysArrayNode,
			        KeyedAttributeUpdateFailureData[].class);

			for (KeyedAttributeUpdateFailureData entry : data) {
				String stringKey = entry.getKey();
				ModelKey key = keyConverter.convertSingle(stringKey);

				KeyedAttributeUpdateFailureImpl failure = new KeyedAttributeUpdateFailureImpl(key, entry);
				failures.add(failure);
			}
		} catch (JsonProcessingException e) {
			throw new ClientResponseSerializationException(e);
		}

		return failures;
	}

}
