package com.dereekb.gae.server.auth.security.token.refresh.exception;

import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;

/**
 * Thrown when a refresh token is deemed invalid.
 * 
 * @author dereekb
 *
 */
public class InvalidRefreshTokenException extends TokenUnauthorizedException {

	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenException() {
		super();
	}

	public InvalidRefreshTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRefreshTokenException(String message) {
		super(message);
	}

	public InvalidRefreshTokenException(Throwable cause) {
		super(cause);
	}

}
