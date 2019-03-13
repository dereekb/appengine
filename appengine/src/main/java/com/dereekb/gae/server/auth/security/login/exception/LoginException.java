package com.dereekb.gae.server.auth.security.login.exception;

/**
 * Login-related exception.
 *
 * @author dereekb
 *
 */
public class LoginException extends Exception {

	private static final long serialVersionUID = 1L;

	private String username;

	public LoginException() {}

	public LoginException(String username) {
		this(username, null);
	}

	public LoginException(String username, String message) {
		super(message);
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
