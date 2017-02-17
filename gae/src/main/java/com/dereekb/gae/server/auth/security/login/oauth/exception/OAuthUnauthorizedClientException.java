package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when the user denies the OAuth screen.
 * 
 * @author dereekb
 *
 */
public class OAuthUnauthorizedClientException extends OAuthException {

	private static final long serialVersionUID = 1L;

	public OAuthUnauthorizedClientException() {
		super();
	}

	public OAuthUnauthorizedClientException(String code, String message) {
		super(code, message);
	}

	public OAuthUnauthorizedClientException(String message) {
		super(message);
	}

	public OAuthUnauthorizedClientException(Throwable cause) {
		super(cause);
	}

}
