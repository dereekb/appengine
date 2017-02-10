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

/**
 * {@link ClientApiResponseBuilder} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientApiResponseBuilderImpl
        implements ClientApiResponseBuilder {

	private ClientApiResponseAccessorBuilder accessorBuilder = ClientApiResponseAccessorBuilderImpl.SINGLETON;

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
		public boolean isSuccessful() {
			return this.clientResponse.isSuccessful();
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
		public boolean getSuccess() {
			return this.apiResponseAccessor.getSuccess();
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

}
