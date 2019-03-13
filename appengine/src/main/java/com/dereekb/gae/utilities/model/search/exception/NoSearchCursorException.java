package com.dereekb.gae.utilities.model.search.exception;

/**
 * Thrown when the search cursor is unavailable.
 * 
 * @author dereekb
 *
 */
public class NoSearchCursorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSearchCursorException() {
		super();
	}

	public NoSearchCursorException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSearchCursorException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSearchCursorException(String message) {
		super(message);
	}

	public NoSearchCursorException(Throwable cause) {
		super(cause);
	}

}
