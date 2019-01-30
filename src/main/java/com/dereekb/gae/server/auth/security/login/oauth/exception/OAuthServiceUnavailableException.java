package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when a requested service is not available.
 * 
 * @author dereekb
 *
 */
public class OAuthServiceUnavailableException extends Exception {

	private static final long serialVersionUID = 1L;

	public OAuthServiceUnavailableException() {
		super();
	}

	public OAuthServiceUnavailableException(String message) {
		super(message);
	}

}
