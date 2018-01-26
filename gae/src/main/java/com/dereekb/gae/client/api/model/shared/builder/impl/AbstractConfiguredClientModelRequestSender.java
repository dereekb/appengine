package com.dereekb.gae.client.api.model.shared.builder.impl;

import java.io.IOException;
import java.util.List;

import com.dereekb.gae.client.api.model.crud.utility.JsonModelResultsSerializer;
import com.dereekb.gae.client.api.model.shared.utility.TypedClientModelKeySerializer;
import com.dereekb.gae.client.api.service.request.ClientRequestUrl;
import com.dereekb.gae.client.api.service.request.impl.ClientRequestUrlImpl;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.extension.data.conversion.TypedBidirectionalConverter;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.databind.JsonNode;

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
        implements JsonModelResultsSerializer<T, O>, TypedModel {

	private String pathFormat;

	private TypedBidirectionalConverter<T, O> typedConverter;
	private TypeModelKeyConverter keyTypeConverter;

	private TypedClientModelKeySerializer keySerializer;

	public AbstractConfiguredClientModelRequestSender(TypedBidirectionalConverter<T, O> typedConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender) throws IllegalArgumentException {
		this(typedConverter, keyTypeConverter, requestSender, null);
	}

	public AbstractConfiguredClientModelRequestSender(TypedBidirectionalConverter<T, O> typedConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender,
	        String pathFormat) throws IllegalArgumentException {
		super(requestSender);
		this.setTypedConverter(typedConverter);
		this.setKeyTypeConverter(keyTypeConverter);
		this.resetKeySerializer();

		if (pathFormat == null) {
			pathFormat = this.getDefaultPathFormat();
		}

		this.setPathFormat(pathFormat);
	}

	@Override
	public final String getModelType() {
		return this.typedConverter.getModelType();
	}

	public TypedBidirectionalConverter<T, O> getTypedConverter() {
		return this.typedConverter;
	}

	public void setTypedConverter(TypedBidirectionalConverter<T, O> typedConverter) {
		if (typedConverter == null) {
			throw new IllegalArgumentException("typedConverter cannot be null.");
		}

		this.typedConverter = typedConverter;
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

	public final Class<O> getDtoType() {
		return this.typedConverter.getModelDataClass();
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
		TypedClientModelKeySerializer serializer = new TypedClientModelKeySerializer(this.getModelType(),
		        this.keyTypeConverter, this.getObjectMapper());
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
		String formattedPath = String.format(this.pathFormat, this.getModelType());
		return new ClientRequestUrlImpl(formattedPath);
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
		return this.typedConverter.convertFrom(dtos);
	}

	@Override
	public List<T> serializeModels(JsonNode jsonData) throws ClientResponseSerializationException {
		List<O> dtos = this.serializeModelDtos(jsonData);
		return this.typedConverter.convertFrom(dtos);
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
		JsonNode jsonData = data.getJsonNode();
		return this.serializeModelDtos(jsonData);
	}

	@Override
	public List<O> serializeModelDtos(JsonNode jsonData) throws ClientResponseSerializationException {
		try {
			return ObjectMapperUtilityBuilderImpl.SINGLETON.make().mapArrayToList(jsonData, this.getDtoType());
		} catch (IOException e) {
			throw new ClientResponseSerializationException(e);
		}
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
