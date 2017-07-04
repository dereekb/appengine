package com.dereekb.gae.model.extension.links.system.mutable.exception;

import com.dereekb.gae.model.extension.links.exception.LinkException;

/**
 * Thrown when a {@link Link} is unavailable.
 *
 * @author dereekb
 *
 */
public class NoReverseLinksException extends LinkException {

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
