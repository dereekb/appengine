package com.dereekb.gae.client.api.model.crud.utility;

import java.util.List;

import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Used for serializing model DTOs from responses.
 * 
 * @author dereekb
 *
 * @param <O>
 *            model dto type
 */
public interface JsonModelDtoResultsSerializer<O> {

	public List<O> serializeModelDtos(ClientApiResponseData data) throws ClientResponseSerializationException;

	public List<O> serializeModelDtos(JsonNode jsonData) throws ClientResponseSerializationException;

}
