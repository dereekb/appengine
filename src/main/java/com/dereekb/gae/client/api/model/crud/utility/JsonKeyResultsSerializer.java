package com.dereekb.gae.client.api.model.crud.utility;

import java.util.List;

import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Used for serializing {@link ModelKey} values from a JSON response.
 * 
 * @author dereekb
 *
 */
public interface JsonKeyResultsSerializer {

	public List<ModelKey> serializeKeys(ClientApiResponseData data) throws ClientResponseSerializationException;

	/**
	 * Serializes {@link ModelKey} values from the input json node.
	 * 
	 * @param keysArrayNode
	 *            {@link JsonNode}. Never {@code null}.
	 * @return {@link List}. Never {@code null}, but can be empty.
	 * 
	 * @throws ClientResponseSerializationException
	 */
	public List<ModelKey> serializeKeys(JsonNode keysArrayNode) throws ClientResponseSerializationException;

}
