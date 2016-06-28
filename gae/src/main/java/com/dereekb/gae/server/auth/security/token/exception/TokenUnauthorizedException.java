package com.dereekb.gae.server.auth.security.token.exception;


/**
 * Thrown when a token attempted to be used is unauthorized or does not exist.
 *
 * @author dereekb
 *
 */
public class TokenUnauthorizedException extends TokenException {

    private static final long serialVersionUID = 1L;

	public TokenUnauthorizedException() {
		this("Token Unauthorized");
	}

	public TokenUnauthorizedException(String message) {
		this(message, null);
	}

	public TokenUnauthorizedException(String message, Throwable e) {
		super(message, e);
	}

}
