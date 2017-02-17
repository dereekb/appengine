package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when the user denies the OAuth screen.
 * 
 * @author dereekb
 *
 */
public class OAuthUnauthorizedClientException extends OAuthAuthenticationException {

	private static final long serialVersionUID = 1L;

	public OAuthUnauthorizedClientException() {
		super();
	}

	public OAuthUnauthorizedClientException(String message, String encodedData) {
		super(message, encodedData);
	}

	public OAuthUnauthorizedClientException(String message) {
		super(message);
	}

}
