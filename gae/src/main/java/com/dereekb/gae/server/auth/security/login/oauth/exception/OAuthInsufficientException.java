package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when the login being used has insufficient information attached to it.
 *
 * @author dereekb
 *
 */
public class OAuthInsufficientException extends OAuthAuthenticationException {

	private static final long serialVersionUID = 1L;

	public OAuthInsufficientException() {
		super();
	}

	public OAuthInsufficientException(String message, String encodedData) {
		super(message, encodedData);
	}

	public OAuthInsufficientException(String message) {
		super(message);
	}

}
