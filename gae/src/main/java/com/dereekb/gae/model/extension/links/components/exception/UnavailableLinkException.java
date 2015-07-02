package com.dereekb.gae.model.extension.links.components.exception;

/**
 * Thrown when a {@link Link} is unavailable.
 *
 * @author dereekb
 *
 */
public class UnavailableLinkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnavailableLinkException() {
		super();
	}

	public UnavailableLinkException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnavailableLinkException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnavailableLinkException(String message) {
		super(message);
	}

	public UnavailableLinkException(Throwable cause) {
		super(cause);
	}

}
