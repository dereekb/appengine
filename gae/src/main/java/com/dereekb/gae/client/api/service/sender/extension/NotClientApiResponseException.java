package com.dereekb.gae.client.api.service.sender.extension;

import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.response.ClientResponse;

/**
 * Thrown when a {@link ClientResponse} cannot be decoded to a proper
 * {@link ClientApiResponse}.
 * 
 * @author dereekb
 *
 */
public class NotClientApiResponseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ClientResponse clientResponse;

	public NotClientApiResponseException(ClientResponse clientResponse) {
		super();
		this.setClientResponse(clientResponse);
	}

	public NotClientApiResponseException(ClientResponse clientResponse, String message) {
		super(message);
		this.setClientResponse(clientResponse);
	}

	public NotClientApiResponseException(ClientResponse clientResponse, String message, Throwable cause) {
		super(message, cause);
		this.setClientResponse(clientResponse);
	}

	public ClientResponse getClientResponse() {
		return this.clientResponse;
	}

	public void setClientResponse(ClientResponse clientResponse) {
		if (clientResponse == null) {
			throw new IllegalArgumentException("clientResponse cannot be null.");
		}

		this.clientResponse = clientResponse;
	}

}
