package com.dereekb.gae.client.api.exception;

/**
 * {@link Exception} thrown when a client request fails for any reason.
 * 
 * @author dereekb
 *
 */
public class ClientRequestFailureException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClientRequestFailureException() {
		super();
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

}
