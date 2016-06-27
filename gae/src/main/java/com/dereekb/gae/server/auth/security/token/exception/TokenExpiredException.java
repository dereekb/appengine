package com.dereekb.gae.server.auth.security.token.exception;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * Thrown when a valid {@link LoginToken} is considered expired.
 *
 * @author dereekb
 *
 */
public class TokenExpiredException extends TokenException {

	private static final long serialVersionUID = 1L;

	public TokenExpiredException() {
		this("Token Expired");
	}

	public TokenExpiredException(String message) {
		super(message);
	}

}
