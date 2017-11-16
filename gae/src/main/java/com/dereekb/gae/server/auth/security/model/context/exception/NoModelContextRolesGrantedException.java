package com.dereekb.gae.server.auth.security.model.context.exception;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;

/**
 * Thrown in cases where a {@link LoginTokenModelContextRoleSet} with no roles
 * is not allowed.
 * 
 * @author dereekb
 *
 */
public class NoModelContextRolesGrantedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoModelContextRolesGrantedException() {
		super();
	}

	public NoModelContextRolesGrantedException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoModelContextRolesGrantedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoModelContextRolesGrantedException(String message) {
		super(message);
	}

	public NoModelContextRolesGrantedException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "NoModelContextRolesGrantedException []";
	}

}
