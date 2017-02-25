package com.dereekb.gae.server.auth.security.token.refresh.exception;

import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;

/**
 * Thrown when a refresh token has expired.
 * 
 * @author dereekb
 *
 */
public class RefreshTokenExpiredException extends TokenExpiredException {

	private static final long serialVersionUID = 1L;

	public RefreshTokenExpiredException() {
		super();
	}

	public RefreshTokenExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public RefreshTokenExpiredException(String message) {
		super(message);
	}

	public RefreshTokenExpiredException(Throwable cause) {
		super(cause);
	}

}
