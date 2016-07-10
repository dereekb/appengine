package com.dereekb.gae.model.extension.links.components.exception;

import com.dereekb.gae.model.extension.links.exception.LinkException;

/**
 * Thrown when a {@link Link} is unavailable.
 *
 * @author dereekb
 *
 */
public class UnavailableLinkException extends LinkException {

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
