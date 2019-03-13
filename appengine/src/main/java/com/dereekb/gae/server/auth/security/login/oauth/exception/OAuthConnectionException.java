package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when a connection error occurs while attempting to connect to a remote
 * OAuth resource.
 *
 * @author dereekb
 *
 */
public class OAuthConnectionException extends OAuthAuthenticationException {

	private static final long serialVersionUID = 1L;

	public OAuthConnectionException() {
		super();
	}

	public OAuthConnectionException(String message, String encodedData) {
		super(message, encodedData);
	}

	public OAuthConnectionException(String message) {
		super(message);
	}

}
