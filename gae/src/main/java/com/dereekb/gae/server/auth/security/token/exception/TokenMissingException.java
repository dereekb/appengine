package com.dereekb.gae.server.auth.security.token.exception;

/**
 * Thrown when the token is missing from the request.
 *
 * @author dereekb
 *
 */
public class TokenMissingException extends TokenException {

	private static final long serialVersionUID = 1L;

	public TokenMissingException() {
		super(TokenExceptionReason.MISSING_TOKEN);
	}

	public TokenMissingException(Throwable cause) {
		super(TokenExceptionReason.MISSING_TOKEN, cause);
	}

	public TokenMissingException(String message) {
		super(TokenExceptionReason.MISSING_TOKEN, message);
	}

	public TokenMissingException(String message, Throwable cause) {
		super(TokenExceptionReason.MISSING_TOKEN, message, cause);
	}

}
