package com.dereekb.gae.server.auth.security.login.exception;

/**
 * Thrown when the specified login resource is unavailable.
 *
 * @author dereekb
 *
 */
public class LoginUnavailableException extends LoginException {

	private static final long serialVersionUID = 1L;

	public LoginUnavailableException(String username, String message) {
		super(username, message);
	}

	public LoginUnavailableException(String username) {
		super(username);
	}

}
