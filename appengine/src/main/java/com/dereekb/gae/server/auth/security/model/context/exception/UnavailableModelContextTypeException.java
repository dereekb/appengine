package com.dereekb.gae.server.auth.security.model.context.exception;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;

/**
 * Thrown by {@link LoginTokenModelContextSet} when a specific type is
 * unavailable.
 * 
 * @author dereekb
 *
 */
public class UnavailableModelContextTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnavailableModelContextTypeException() {
		super();
	}

	public UnavailableModelContextTypeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnavailableModelContextTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableModelContextTypeException(String message) {
		super(message);
	}

	public UnavailableModelContextTypeException(Throwable cause) {
		super(cause);
	}

}
