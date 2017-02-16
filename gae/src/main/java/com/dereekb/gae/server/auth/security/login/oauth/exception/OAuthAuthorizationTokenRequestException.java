package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * Thrown when an authorization token request fails.
 *
 * @author dereekb
 *
 */
public class OAuthAuthorizationTokenRequestException extends OAuthException {

	private static final long serialVersionUID = 1L;

	private String code;
	private String type;
	private String message;

	public OAuthAuthorizationTokenRequestException(String code, String type, String message) {
		this(code, type, message, null);
	}

	public OAuthAuthorizationTokenRequestException(String code, String type, String message, Throwable e) {
		super(e);
		this.setCode(code);
		this.setType(type);
		this.setMessage(message);
	}

	@Deprecated
	public OAuthAuthorizationTokenRequestException(String code, String message) {
		super(code, message);
	}

	@Deprecated
	public OAuthAuthorizationTokenRequestException(Integer httpCode, String httpStatusMessage) {
		super();
		this.code = httpCode.toString();
		this.message = httpStatusMessage;
	}

	@Deprecated
	public OAuthAuthorizationTokenRequestException(String message) {
		super(message);
	}

	public OAuthAuthorizationTokenRequestException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "OAuthAuthorizationTokenRequestException []";
	}

}
