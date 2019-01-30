package com.dereekb.gae.server.auth.security.login.oauth.exception;

import com.dereekb.gae.server.auth.security.login.exception.LoginAuthenticationException;

/**
 * General OAuthException.
 *
 * @author dereekb
 *
 */
public class OAuthAuthenticationException extends LoginAuthenticationException {

	private static final long serialVersionUID = 1L;

	public OAuthAuthenticationException() {
		super();
	}

	public OAuthAuthenticationException(String message) {
		super(message);
	}

	public OAuthAuthenticationException(String message, String encodedData) {
		super(message, encodedData);
	}

	@Override
	public String toString() {
		return "OAuthException []";
	}

}
