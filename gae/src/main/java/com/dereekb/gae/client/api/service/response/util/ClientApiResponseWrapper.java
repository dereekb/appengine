package com.dereekb.gae.client.api.service.response.util;

import java.util.Map;

import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Abstract wrapper around a {@link ClientApiResponse} that forwards all
 * functions to the wrapped value.
 * 
 * @author dereekb
 *
 */
public abstract class ClientApiResponseWrapper
        implements ClientApiResponse {

	protected final ClientApiResponse response;

	public ClientApiResponseWrapper(ClientApiResponse response) {
		this.response = response;
	}

	// MARK: ClientApiResponse
	@Override
	public boolean isSuccessful() {
		return this.response.isSuccessful();
	}

	@Override
	public ClientApiResponseData getPrimaryData() throws NoClientResponseDataException {
		return this.response.getPrimaryData();
	}

	@Override
	public Map<String, ClientApiResponseData> getIncludedData() {
		return this.response.getIncludedData();
	}

	@Override
	public ClientResponseError getError() {
		return this.response.getError();
	}

	@Override
	public int getStatus() {
		return this.response.getStatus();
	}

	@Override
	public String getResponseData() {
		return this.response.getResponseData();
	}

	@Override
	public JsonNode getApiResponseJsonNode() {
		return this.response.getApiResponseJsonNode();
	}

	@Override
	public String toString() {
		return "ClientApiResponseWrapper [response=" + this.response + "]";
	}

}
