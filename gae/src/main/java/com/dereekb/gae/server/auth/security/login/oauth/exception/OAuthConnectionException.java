package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when a connection error occurs while attempting to connect to a remote
 * OAuth resource.
 *
 * @author dereekb
 *
 */
public class OAuthConnectionException extends OAuthException {

	private static final long serialVersionUID = 1L;

	public OAuthConnectionException() {
		super();
	}

	public OAuthConnectionException(Throwable cause) {
		super(cause);
	}

}
