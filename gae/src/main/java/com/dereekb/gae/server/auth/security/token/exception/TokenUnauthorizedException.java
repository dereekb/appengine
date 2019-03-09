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
		super(TokenExceptionReason.INVALID_TOKEN);
	}

	public TokenUnauthorizedException(Throwable cause) {
		super(TokenExceptionReason.INVALID_TOKEN, cause);
	}

	public TokenUnauthorizedException(String message) {
		super(TokenExceptionReason.INVALID_TOKEN, message);
	}

	public TokenUnauthorizedException(String message, Throwable cause) {
		super(TokenExceptionReason.INVALID_TOKEN, message, cause);
	}

}
