package com.dereekb.gae.server.auth.security.token.parameter.exception;

/**
 * Thrown by {@link AuthenticationParameterDecoder} for invalid input.
 * 
 * @author dereekb
 *
 */
public class InvalidAuthStringException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidAuthStringException() {
		super();
	}

	public InvalidAuthStringException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAuthStringException(String message) {
		super(message);
	}

	public InvalidAuthStringException(Throwable cause) {
		super(cause);
	}

}
