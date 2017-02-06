package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when an authorization token request fails.
 *
 * @author dereekb
 *
 */
public class OAuthAuthorizationTokenRequestException extends OAuthException {

	private static final long serialVersionUID = 1L;

	private Integer httpCode;
	private String httpStatusMessage;

	public OAuthAuthorizationTokenRequestException() {}

	public OAuthAuthorizationTokenRequestException(String code, String message) {
		super(code, message);
	}

	public OAuthAuthorizationTokenRequestException(Integer httpCode, String httpStatusMessage) {
		super();
		this.httpCode = httpCode;
		this.httpStatusMessage = httpStatusMessage;
	}

	public OAuthAuthorizationTokenRequestException(String message) {
		super(message);
	}

	public OAuthAuthorizationTokenRequestException(Throwable cause) {
		super(cause);
	}

	public Integer getHttpCode() {
		return this.httpCode;
	}

	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}

	public String getHttpStatusMessage() {
		return this.httpStatusMessage;
	}

	public void setHttpStatusMessage(String httpStatusMessage) {
		this.httpStatusMessage = httpStatusMessage;
	}

	@Override
	public String toString() {
		return "OAuthAuthorizationTokenRequestException []";
	}

}
