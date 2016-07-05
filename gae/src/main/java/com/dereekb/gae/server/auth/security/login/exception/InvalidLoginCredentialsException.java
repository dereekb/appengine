package com.dereekb.gae.server.auth.security.login.exception;

/**
 * Thrown if the specified login credentials are invalid or do not match.
 *
 * @author dereekb
 *
 */
public class InvalidLoginCredentialsException extends LoginException {

	private static final long serialVersionUID = 1L;

	public InvalidLoginCredentialsException(String username, String message) {
		super(username, message);
	}

	public InvalidLoginCredentialsException(String username) {
		super(username);
	}

}
