package com.dereekb.gae.server.auth.security.login.oauth.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;

/**
 * General OAuthException.
 *
 * @author dereekb
 *
 */
public class OAuthException extends LoginAuthenticationException {

	private static final long serialVersionUID = 1L;

	public OAuthException() {
		super();
	}

	public OAuthException(String code, String message) {
		super(code, message);
	}

	public OAuthException(String message) {
		super(message);
	}

	public OAuthException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "OAuthException [code=" + this.code + ", getMessage()=" + this.getMessage() + "]";
	}

}
