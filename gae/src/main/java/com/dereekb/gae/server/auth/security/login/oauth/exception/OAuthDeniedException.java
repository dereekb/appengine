package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when the OAuth credentials are denied by the remote server.
 *
 * @author dereekb
 *
 */
public class OAuthDeniedException extends OAuthException {

	private static final long serialVersionUID = 1L;

	public OAuthDeniedException() {}

	public OAuthDeniedException(String code, String message) {
		super(code, message);
	}

}
