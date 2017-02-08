package com.dereekb.gae.client.api.exception;

/**
 * {@link ClientRequestFailureException} thrown when a client request fails due
 * to authentication reasons.
 * 
 * @author dereekb
 *
 */
public class ClientAuthenticationException extends ClientRequestFailureException {

	private static final long serialVersionUID = 1L;

	public ClientAuthenticationException() {}

	public ClientAuthenticationException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClientAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientAuthenticationException(String message) {
		super(message);
	}

	public ClientAuthenticationException(Throwable cause) {
		super(cause);
	}

}
