package com.dereekb.gae.client.api.model.shared.utility;

import java.util.List;

import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseErrorInfo;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility for serializing {@link ModelKey} values from client responses and
 * JSON.
 * 
 * @author dereekb
 *
 */
public class ClientModelKeySerializer {

	private ObjectMapper objectMapper;
	private TypeModelKeyConverter keyTypeConverter;

	public ClientModelKeySerializer(TypeModelKeyConverter keyTypeConverter) {
		this(keyTypeConverter, new ObjectMapper());
	}

	public ClientModelKeySerializer(TypeModelKeyConverter keyTypeConverter, ObjectMapper objectMapper) {
		this.setKeyTypeConverter(keyTypeConverter);
		this.setObjectMapper(objectMapper);
	}

	public ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		if (objectMapper == null) {
			throw new IllegalArgumentException("objectMapper cannot be null.");
		}

		this.objectMapper = objectMapper;
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

	// MARK: Utility
	/**
	 * Serializes {@link ModelKey} values from the input error info.
	 * 
	 * @param type
	 *            Model type.
	 * @param keysErrorInfo
	 *            {@link ClientResponseErrorInfo}. Never {@code null}.
	 * @return {@link List}. Never {@code null}, but can be empty.
	 * 
	 * @throws ClientResponseSerializationException
	 */
	public List<ModelKey> serializeKeysFromErrorInfoData(String type,
	                                                     ClientResponseErrorInfo keysErrorInfo)
	        throws ClientResponseSerializationException {
		JsonNode keysData = keysErrorInfo.getErrorData();
		return this.serializeKeys(type, keysData);
	}

	public List<ModelKey> serializeKeys(String type,
	                                    ClientApiResponseData data)
	        throws ClientResponseSerializationException {
		JsonNode keysArrayNode = data.getDataJsonNode();
		return this.serializeKeys(type, keysArrayNode);
	}

	/**
	 * Serializes {@link ModelKey} values from the input json node.
	 * 
	 * @param type
	 *            Model type.
	 * @param keysArrayNode
	 *            {@link JsonNode}. Never {@code null}.
	 * @return {@link List}. Never {@code null}, but can be empty.
	 * 
	 * @throws ClientResponseSerializationException
	 */
	public List<ModelKey> serializeKeys(String type,
	                                    JsonNode keysArrayNode)
	        throws ClientResponseSerializationException {
		List<ModelKey> objectKeys = null;
		ObjectMapper mapper = this.getObjectMapper();

		try {
			String[] keys = mapper.treeToValue(keysArrayNode, String[].class);
			List<String> keysList = ListUtility.toList(keys);
			objectKeys = this.keyTypeConverter.convertKeys(type, keysList);
		} catch (JsonProcessingException e) {
			throw new ClientResponseSerializationException(e);
		}

		return objectKeys;
	}

	@Override
	public String toString() {
		return "ClientModelKeySerializer [objectMapper=" + this.objectMapper + ", keyTypeConverter="
		        + this.keyTypeConverter + "]";
	}

}
