package com.dereekb.gae.server.auth.security.login.exception;

/**
 * Thrown if a login with that name exists.
 *
 * @author dereekb
 *
 */
public class LoginExistsException extends LoginException {

	private static final long serialVersionUID = 1L;

	public LoginExistsException() {
		super(null, "The login exists.");
	}

	public LoginExistsException(String username) {
		super(username);
	}

	@Override
	public String toString() {
		return "LoginExistsException []";
	}

}
