package com.dereekb.gae.client.api.service.sender.impl;

import java.util.Collections;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.utilities.data.impl.ObjectMapperUtilityBuilderImpl;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Abstract {@link SerializedClientApiResponse} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            serialized response type
 */
public abstract class AbstractSerializedClientApiResponseImpl<T>
        implements SerializedClientApiResponse<T> {

	@Override
	public JsonNode getApiResponseJsonNode() {
		return ObjectMapperUtilityBuilderImpl.MAPPER.createObjectNode();
	}

	@Override
	public ClientApiResponseData getPrimaryData() throws NoClientResponseDataException {
		throw new NoClientResponseDataException();
	}

	@Override
	public Map<String, ClientApiResponseData> getIncludedData() {
		return Collections.emptyMap();
	}

	@Override
	public ClientResponseError getError() {
		return null;
	}

	@Override
	public boolean isSuccessful() {
		return true;
	}

	@Override
	public int getStatus() {
		return 200;
	}

	@Override
	public String getResponseData() {
		return null;
	}

	@Override
	public abstract T getSerializedResponse()
	        throws ClientRequestFailureException,
	            ClientResponseSerializationException;

}
