package com.dereekb.gae.client.api.model.shared.builder.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.utility.JsonModelResultsSerializer;
import com.dereekb.gae.client.api.model.shared.utility.TypedClientModelKeySerializer;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestUrlImpl;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link AbstractSecuredClientModelRequestSender} extension with configuration
 * for serializing models from responses.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 * @param <R>
 *            request type
 * @param <S>
 *            serialized response type
 */
public abstract class AbstractConfiguredClientModelRequestSender<T extends UniqueModel, O, R, S> extends AbstractSecuredClientModelRequestSender<R, S>
        implements JsonModelResultsSerializer<T, O> {

	private String type;
	private String pathFormat;

	private Class<O> dtoType;
	private BidirectionalConverter<T, O> dtoConverter;
	private TypeModelKeyConverter keyTypeConverter;

	private TypedClientModelKeySerializer keySerializer;

	public AbstractConfiguredClientModelRequestSender(String type,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		this(type, null, dtoType, dtoConverter, keyTypeConverter, requestSender);
	}

	public AbstractConfiguredClientModelRequestSender(String type,
	        String pathFormat,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		super(requestSender);
		this.setType(type);
		this.setDtoType(dtoType);
		this.setDtoConverter(dtoConverter);
		this.setKeyTypeConverter(keyTypeConverter);
		this.resetKeySerializer();

		if (pathFormat == null) {
			pathFormat = this.getDefaultPathFormat();
		}

		this.setPathFormat(pathFormat);
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

	public Class<O> getDtoType() {
		return this.dtoType;
	}

	public void setDtoType(Class<O> dtoType) {
		if (dtoType == null) {
			throw new IllegalArgumentException("dtoType cannot be null.");
		}

		this.dtoType = dtoType;
	}

	public BidirectionalConverter<T, O> getDtoConverter() {
		return this.dtoConverter;
	}

	public void setDtoConverter(BidirectionalConverter<T, O> dtoConverter) {
		if (dtoConverter == null) {
			throw new IllegalArgumentException("dtoConverter cannot be null.");
		}

		this.dtoConverter = dtoConverter;
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

	protected TypedClientModelKeySerializer getKeySerializer() {
		return this.keySerializer;
	}

	protected void resetKeySerializer() {
		TypedClientModelKeySerializer serializer = new TypedClientModelKeySerializer(this.type, this.keyTypeConverter,
		        this.getObjectMapper());
		this.setKeySerializer(serializer);
	}

	protected void setKeySerializer(TypedClientModelKeySerializer keySerializer) {
		if (keySerializer == null) {
			throw new IllegalArgumentException("keySerializer cannot be null.");
		}

		this.keySerializer = keySerializer;
	}

	// MARK: Abstract
	protected abstract String getDefaultPathFormat();

	// MARK: Utility
	public ClientRequestUrl makeRequestUrl() {
		String formattedPath = String.format(this.pathFormat, this.type);
		return new ClientRequestUrlImpl(formattedPath);
	}

	/**
	 * Asserts that the request was successful.
	 * 
	 * @param clientResponse
	 *            {@link ClientApiResponse}. Never {@code null}.
	 * @throws ClientRequestFailureException
	 *             asserted exception.
	 */
	public void assertSuccessfulResponse(ClientApiResponse clientResponse) throws ClientRequestFailureException {
		if (clientResponse.getSuccess() == false) {
			throw new ClientRequestFailureException(clientResponse);
		}
	}

	// MARK: Model Serialization
	/**
	 * Serializes models from the input data.
	 * 
	 * @param data
	 *            {@link ClientApiResponseData}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 * @throws ClientResponseSerializationException
	 *             if the serialization fails.
	 */
	@Override
	public List<T> serializeModels(ClientApiResponseData data) throws ClientResponseSerializationException {
		List<O> dtos = this.serializeModelDtos(data);
		return this.dtoConverter.convertFrom(dtos);
	}

	@Override
	public List<T> serializeModels(JsonNode jsonData) throws ClientResponseSerializationException {
		List<O> dtos = this.serializeModelDtos(jsonData);
		return this.dtoConverter.convertFrom(dtos);
	}

	/**
	 * Serializes model DTOs from the input data.
	 * 
	 * @param data
	 *            {@link ClientApiResponseData}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 * @throws ClientResponseSerializationException
	 *             if the serialization fails.
	 */
	@Override
	public List<O> serializeModelDtos(ClientApiResponseData data) throws ClientResponseSerializationException {
		JsonNode jsonData = data.getDataJsonNode();
		return this.serializeModelDtos(jsonData);
	}

	@Override
	public List<O> serializeModelDtos(JsonNode jsonData) throws ClientResponseSerializationException {
		ObjectMapper mapper = this.getObjectMapper();
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

	// MARK: Key Serialization
	public List<ModelKey> serializeKeysFromErrorInfoData(ClientResponseErrorInfo keysErrorInfo)
	        throws ClientResponseSerializationException {
		return this.keySerializer.serializeKeysFromErrorInfoData(keysErrorInfo);
	}

	@Override
	public List<ModelKey> serializeKeys(ClientApiResponseData data) throws ClientResponseSerializationException {
		return this.keySerializer.serializeKeys(data);
	}

	@Override
	public List<ModelKey> serializeKeys(JsonNode keysArrayNode) throws ClientResponseSerializationException {
		return this.keySerializer.serializeKeys(keysArrayNode);
	}

}
