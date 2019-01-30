package com.dereekb.gae.utilities.model.lazy.exception;

/**
 * Thrown when a {@link Source} object is unavailable.
 * 
 * @author dereekb
 *
 */
public class UnavailableSourceObjectException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnavailableSourceObjectException() {
		super();
	}

	public UnavailableSourceObjectException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnavailableSourceObjectException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableSourceObjectException(String message) {
		super(message);
	}

	public UnavailableSourceObjectException(Throwable cause) {
		super(cause);
	}

}
