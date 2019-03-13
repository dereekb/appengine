package com.dereekb.gae.server.auth.security.login.exception;

/**
 * {@link LoginException} thrown when a registration request is rejected.
 *
 * @author dereekb
 *
 */
public class LoginRegistrationRejectedException extends LoginException {

	private static final long serialVersionUID = 1L;

	public LoginRegistrationRejectedException() {
		super(null, "Registration was rejected.");
	}

	public LoginRegistrationRejectedException(String message) {
		super(null, message);
	}

}
