package com.dereekb.gae.server.auth.security.login.exception;

/**
 * General authentication exception.
 *
 * @author dereekb
 *
 */
public abstract class LoginAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected String code;

	public LoginAuthenticationException() {}

	public LoginAuthenticationException(String code, String message) {
		super(message);
		this.setCode(code);
	}

	public LoginAuthenticationException(String message) {
		super(message);
	}

	public LoginAuthenticationException(Throwable cause) {
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
		return "SecurityException [code=" + this.code + ", getMessage()=" + this.getMessage() + "]";
	}

}
