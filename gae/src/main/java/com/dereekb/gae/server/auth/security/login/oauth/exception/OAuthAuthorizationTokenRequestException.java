package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when an authorization token request fails.
 *
 * @author dereekb
 *
 */
public class OAuthAuthorizationTokenRequestException extends OAuthException {

	private static final long serialVersionUID = 1L;

	private String type;

	public OAuthAuthorizationTokenRequestException(String code, String type, String message) {
		this(code, type, message, null);
	}

	public OAuthAuthorizationTokenRequestException(String code, String type, String message, Throwable e) {
		super(code, message, e);
		this.setType(type);
	}

	public OAuthAuthorizationTokenRequestException(String code, String message) {
		super(code, message);
	}

	@Deprecated
	public OAuthAuthorizationTokenRequestException(Integer httpCode, String httpStatusMessage) {
		super(httpCode.toString(), httpStatusMessage);
	}

	@Deprecated
	public OAuthAuthorizationTokenRequestException(String message) {
		super(message);
	}

	public OAuthAuthorizationTokenRequestException(Throwable cause) {
		super(cause);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "OAuthAuthorizationTokenRequestException []";
	}

}
