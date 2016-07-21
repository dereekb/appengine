package com.dereekb.gae.server.auth.security.login.oauth.exception;

/**
 * General OAuthException.
 *
 * @author dereekb
 *
 */
public class OAuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	public OAuthException() {}

	public OAuthException(String code, String message) {
		super(message);
		this.setCode(code);
	}

	public OAuthException(String message) {
		super(message);
	}

	public OAuthException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "OAuthException [code=" + this.code + ", getMessage()=" + this.getMessage() + "]";
	}

}
