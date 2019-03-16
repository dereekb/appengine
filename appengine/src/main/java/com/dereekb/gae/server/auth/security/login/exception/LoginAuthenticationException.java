package com.dereekb.gae.server.auth.security.login.exception;

/**
 * Abstract login authentication exception.
 * 
 * Contains optional encoded data that can be passed to API responses.
 *
 * @author dereekb
 *
 */
public abstract class LoginAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String encodedData;

	public LoginAuthenticationException() {}

	public LoginAuthenticationException(String message) {
		super(message);
	}

	public LoginAuthenticationException(String message, String encodedData) {
		super(message);
		this.setEncodedData(encodedData);
	}

	public String getEncodedData() {
		return this.encodedData;
	}

	public void setEncodedData(String encodedData) {
		this.encodedData = encodedData;
	}

}
