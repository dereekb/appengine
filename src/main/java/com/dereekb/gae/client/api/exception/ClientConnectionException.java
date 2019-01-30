package com.dereekb.gae.client.api.exception;

/**
 * {@link ClientRequestFailureException} thrown when a client request fails due
 * to connection reasons.
 * 
 * @author dereekb
 *
 */
public class ClientConnectionException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	public ClientConnectionException() {}

	public ClientConnectionException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientConnectionException(String message) {
		super(message);
	}

	public ClientConnectionException(Throwable cause) {
		super(cause);
	}

}
