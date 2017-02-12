package com.dereekb.gae.client.api.exception;

import com.dereekb.gae.client.api.service.response.ClientResponse;

/**
 * {@link Exception} thrown when a client request fails for any reason.
 * 
 * @author dereekb
 *
 */
public class ClientRequestFailureException extends Exception {

	private static final long serialVersionUID = 1L;

	private ClientResponse response;

	public ClientRequestFailureException() {
		super();
	}

	public ClientRequestFailureException(ClientResponse response) {
		this.setResponse(response);
	}

	public ClientRequestFailureException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientRequestFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientRequestFailureException(String message) {
		super(message);
	}

	public ClientRequestFailureException(Throwable cause) {
		super(cause);
	}

	public ClientResponse getResponse() {
		return this.response;
	}

	public void setResponse(ClientResponse response) {
		this.response = response;
	}

}
