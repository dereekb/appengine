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
		super(TokenExceptionReason.EXPIRED_TOKEN);
	}

	public TokenExpiredException(Throwable cause) {
		super(TokenExceptionReason.EXPIRED_TOKEN, cause);
	}

	public TokenExpiredException(String message) {
		super(TokenExceptionReason.EXPIRED_TOKEN, message);
	}

	public TokenExpiredException(String message, Throwable cause) {
		super(TokenExceptionReason.EXPIRED_TOKEN, message, cause);
	}

}
