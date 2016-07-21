package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when an authorization token request fails.
 *
 * @author dereekb
 *
 */
public class OAuthAuthorizationTokenRequestException extends OAuthException {

	private static final long serialVersionUID = 1L;

	public OAuthAuthorizationTokenRequestException() {}

	public OAuthAuthorizationTokenRequestException(String code, String message) {
		super(code, message);
	}

	public OAuthAuthorizationTokenRequestException(String message) {
		super(message);
	}

	public OAuthAuthorizationTokenRequestException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "OAuthAuthorizationTokenRequestException []";
	}

}
