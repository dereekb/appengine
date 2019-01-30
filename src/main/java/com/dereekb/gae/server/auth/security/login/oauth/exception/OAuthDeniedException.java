package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when the OAuth credentials are denied by the remote server.
 *
 * @author dereekb
 *
 */
public class OAuthDeniedException extends OAuthAuthenticationException {

	private static final long serialVersionUID = 1L;

	public OAuthDeniedException() {
		super();
	}

	public OAuthDeniedException(String message, String encodedData) {
		super(message, encodedData);
	}

	public OAuthDeniedException(String message) {
		super(message);
	}

}
