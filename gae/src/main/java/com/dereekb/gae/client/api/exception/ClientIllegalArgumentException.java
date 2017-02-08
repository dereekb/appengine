package com.dereekb.gae.client.api.exception;

/**
 * {@link ClientRequestFailureException} thrown when a client request fails due
 * to the request containing illegal arguments.
 * 
 * @author dereekb
 *
 */
public class ClientIllegalArgumentException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	public ClientIllegalArgumentException() {}

	public ClientIllegalArgumentException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientIllegalArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientIllegalArgumentException(String message) {
		super(message);
	}

	public ClientIllegalArgumentException(Throwable cause) {
		super(cause);
	}

}
