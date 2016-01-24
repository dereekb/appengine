package com.dereekb.gae.model.extension.links.deleter.exception;

import com.dereekb.gae.model.extension.links.components.system.LinkSystem;

/**
 * Thrown by {@link LinkSystem} with an unavailable type is requested.
 *
 * @author dereekb
 *
 */
public class UnregisteredDeleterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnregisteredDeleterException() {
		super();
	}

	public UnregisteredDeleterException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnregisteredDeleterException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnregisteredDeleterException(String message) {
		super(message);
	}

	public UnregisteredDeleterException(Throwable cause) {
		super(cause);
	}

}
