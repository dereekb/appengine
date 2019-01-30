package com.dereekb.gae.server.auth.security.token.refresh.exception;

import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;

/**
 * Thrown when a token is no longer usable due to an authentication purge.
 * 
 * @author dereekb
 *
 */
public class AuthenticationPurgeException extends TokenExpiredException {

	private static final long serialVersionUID = 1L;

	public AuthenticationPurgeException() {
		super();
	}

	public AuthenticationPurgeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationPurgeException(String message) {
		super(message);
	}

	public AuthenticationPurgeException(Throwable cause) {
		super(cause);
	}

}
