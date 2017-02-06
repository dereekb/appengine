package com.dereekb.gae.server.auth.old.security.filter.exception;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Exception raised when a {@link LoginPointer} exists, but the {@link Login} is
 * unavailable.
 *
 * @author dereekb
 *
 */
public final class LoginInconsistencyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final LoginPointer loginPointer;

	public LoginInconsistencyException(LoginPointer loginPointer) {
		if (loginPointer == null) {
			throw new IllegalArgumentException("The LoginPointer cannot be null.");
		}

		this.loginPointer = loginPointer;
	}

	public LoginPointer getLoginPointer() {
		return this.loginPointer;
	}

}
