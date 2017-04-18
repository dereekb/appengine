package com.dereekb.gae.client.api.model.crud.utility;

import java.util.List;

import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Used for serializing models from responses.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface JsonFullModelResultsSerializer<T> {

	public List<T> serializeModels(ClientApiResponseData data) throws ClientResponseSerializationException;

	public List<T> serializeModels(JsonNode jsonData) throws ClientResponseSerializationException;

}
