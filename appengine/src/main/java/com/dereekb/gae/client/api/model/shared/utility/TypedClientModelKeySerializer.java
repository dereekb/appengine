package com.dereekb.gae.client.api.model.shared.utility;

import java.util.List;

import com.dereekb.gae.client.api.model.crud.utility.JsonKeyResultsSerializer;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link ClientModelKeySerializer} extension that implements
 * {@link JsonKeyResultsSerializer}.
 * 
 * @author dereekb
 *
 */
public class TypedClientModelKeySerializer extends ClientModelKeySerializer
        implements JsonKeyResultsSerializer {

	private String type;

	public TypedClientModelKeySerializer(String type, TypeModelKeyConverter keyTypeConverter) {
		super(keyTypeConverter);
		this.setType(type);
	}

	public TypedClientModelKeySerializer(String type,
	        TypeModelKeyConverter keyTypeConverter,
	        ObjectMapper objectMapper) {
		super(keyTypeConverter, objectMapper);
		this.setType(type);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("Type cannot be null or empty.");
		}

		this.type = type;
	}

	// MARK: Utility
	/**
	 * Serializes {@link ModelKey} values from the input error info.
	 * 
	 * @param keysErrorInfo
	 *            {@link ClientResponseErrorInfo}. Never {@code null}.
	 * @return {@link List}. Never {@code null}, but can be empty.
	 */
	public List<ModelKey> serializeKeysFromErrorInfoData(ClientResponseErrorInfo keysErrorInfo)
	        throws ClientResponseSerializationException {
		return super.serializeKeysFromErrorInfoData(this.type, keysErrorInfo);
	}

	@Override
	public List<ModelKey> serializeKeys(ClientApiResponseData data) throws ClientResponseSerializationException {
		return super.serializeKeys(this.type, data);
	}

	@Override
	public List<ModelKey> serializeKeys(JsonNode keysArrayNode) throws ClientResponseSerializationException {
		return this.serializeKeys(this.type, keysArrayNode);
	}

}
