package com.dereekb.gae.client.api.service.response.builder.impl;

import java.util.Map;

import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.ClientApiResponseAccessor;
import com.dereekb.gae.client.api.service.response.ClientResponse;
import com.dereekb.gae.client.api.service.response.builder.ClientApiResponseAccessorBuilder;
import com.dereekb.gae.client.api.service.response.builder.ClientApiResponseBuilder;
import com.dereekb.gae.client.api.service.response.data.ClientApiResponseData;
import com.dereekb.gae.client.api.service.response.error.ClientResponseError;
import com.dereekb.gae.client.api.service.response.exception.NoClientResponseDataException;
import com.dereekb.gae.client.api.service.sender.extension.NotClientApiResponseException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * {@link ClientApiResponseBuilder} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientApiResponseBuilderImpl
        implements ClientApiResponseBuilder {

	public static final ClientApiResponseBuilder SINGLETON = new ClientApiResponseBuilderImpl();

	private ClientApiResponseAccessorBuilder accessorBuilder;

	public ClientApiResponseBuilderImpl() {
		this.setAccessorBuilder(ClientApiResponseAccessorBuilderImpl.SINGLETON);
	}

	public ClientApiResponseBuilderImpl(ClientApiResponseAccessorBuilder accessorBuilder) {
		this.setAccessorBuilder(accessorBuilder);
	}

	public ClientApiResponseAccessorBuilder getAccessorBuilder() {
		return this.accessorBuilder;
	}

	public void setAccessorBuilder(ClientApiResponseAccessorBuilder accessorBuilder) {
		if (accessorBuilder == null) {
			throw new IllegalArgumentException("accessorBuilder cannot be null.");
		}

		this.accessorBuilder = accessorBuilder;
	}

	// MARK: ClientApiRequestBuilder
	@Override
	public ClientApiResponse buildApiResponse(ClientResponse response) throws NotClientApiResponseException {
		return new ClientApiResponseImpl(response);
	}

	// MARK: Internal
	public class ClientApiResponseImpl
	        implements ClientApiResponse {

		private final ClientResponse clientResponse;
		private final ClientApiResponseAccessor apiResponseAccessor;

		public ClientApiResponseImpl(ClientResponse clientResponse) throws NotClientApiResponseException {
			this.clientResponse = clientResponse;
			this.apiResponseAccessor = ClientApiResponseBuilderImpl.this.accessorBuilder.buildAccessor(clientResponse);
		}

		@Override
		public int getStatus() {
			return this.clientResponse.getStatus();
		}

		@Override
		public String getResponseData() {
			return this.clientResponse.getResponseData();
		}

		// MARK: ClientApiResponse
		@Override
		public boolean isSuccessful() {
			return this.apiResponseAccessor.isSuccessful();
		}

		@Override
		public JsonNode getApiResponseJsonNode() {
			return this.apiResponseAccessor.getApiResponseJsonNode();
		}

		@Override
		public ClientApiResponseData getPrimaryData() throws NoClientResponseDataException {
			return this.apiResponseAccessor.getPrimaryData();
		}

		@Override
		public Map<String, ClientApiResponseData> getIncludedData() {
			return this.apiResponseAccessor.getIncludedData();
		}

		@Override
		public ClientResponseError getError() {
			return this.apiResponseAccessor.getError();
		}

		@Override
		public String toString() {
			return "ClientApiResponseImpl [clientResponse=" + this.clientResponse + ", decodedResponse="
			        + this.apiResponseAccessor + "]";
		}

	}

	@Override
	public String toString() {
		return "ClientApiResponseBuilderImpl [accessorBuilder=" + this.accessorBuilder + "]";
	}

}
