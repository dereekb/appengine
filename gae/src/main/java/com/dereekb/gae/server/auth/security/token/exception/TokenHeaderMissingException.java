package com.dereekb.gae.server.auth.security.token.exception;

/**
 * Thrown when the token is missing from the request.
 *
 * @author dereekb
 *
 */
public class TokenHeaderMissingException extends TokenException {

	private static final long serialVersionUID = 1L;

	public TokenHeaderMissingException() {
		this("Token Expired");
	}

	public TokenHeaderMissingException(String message) {
		super(message);
	}

}
