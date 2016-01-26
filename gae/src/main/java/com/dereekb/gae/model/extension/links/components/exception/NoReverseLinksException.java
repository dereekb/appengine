package com.dereekb.gae.model.extension.links.components.exception;

/**
 * Thrown when a {@link Link} is unavailable.
 *
 * @author dereekb
 *
 */
public class NoReverseLinksException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoReverseLinksException() {
		super();
	}

	public NoReverseLinksException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoReverseLinksException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoReverseLinksException(String message) {
		super(message);
	}

	public NoReverseLinksException(Throwable cause) {
		super(cause);
	}

}
