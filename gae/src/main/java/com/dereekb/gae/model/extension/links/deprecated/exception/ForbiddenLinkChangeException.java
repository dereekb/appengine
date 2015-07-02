package com.dereekb.gae.model.extension.links.deprecated.exception;

/**
 *
 * @author dereekb
 */
public final class ForbiddenLinkChangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ForbiddenLinkChangeException() {
		super();
	}

	public ForbiddenLinkChangeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ForbiddenLinkChangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenLinkChangeException(String message) {
		super(message);
	}

	public ForbiddenLinkChangeException(Throwable cause) {
		super(cause);
	}

}
