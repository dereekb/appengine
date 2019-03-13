package com.dereekb.gae.server.auth.security.login.exception;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Thrown when a {@link LoginPointer} attempts to be registered with a
 * {@link Login} but is already linked.
 * 
 * @author dereekb
 *
 */
public class LoginPointerRegisteredException extends LoginRegistrationRejectedException {

	private static final long serialVersionUID = 1L;

	public LoginPointerRegisteredException() {
		this("LoginPointer was already linked to another login.");
	}

	public LoginPointerRegisteredException(LoginPointer pointer) {
		this(String.format("LoginPointer '%s' was already linked to another login.", pointer.getIdentifier()));
	}

	public LoginPointerRegisteredException(String message) {
		super(message);
	}

}
