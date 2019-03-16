package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when an authorization token request fails.
 *
 * @author dereekb
 *
 */
public class OAuthAuthorizationTokenRequestException extends OAuthAuthenticationException {

	private static final long serialVersionUID = 1L;

	public OAuthAuthorizationTokenRequestException() {
		super();
	}

	public OAuthAuthorizationTokenRequestException(String message, String encodedData) {
		super(message, encodedData);
	}

	public OAuthAuthorizationTokenRequestException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return "OAuthAuthorizationTokenRequestException []";
	}

}
