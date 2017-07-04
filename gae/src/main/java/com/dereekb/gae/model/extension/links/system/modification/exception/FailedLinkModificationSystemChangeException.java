package com.dereekb.gae.model.extension.links.system.modification.exception;

/**
 * Exception thrown when submitted changes fail.
 * 
 * @author dereekb
 *
 */
public class FailedLinkModificationSystemChangeException extends Exception {

	private static final long serialVersionUID = 1L;

	public FailedLinkModificationSystemChangeException() {
		super();
	}

	public FailedLinkModificationSystemChangeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FailedLinkModificationSystemChangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedLinkModificationSystemChangeException(String message) {
		super(message);
	}

	public FailedLinkModificationSystemChangeException(Throwable cause) {
		super(cause);
	}

}
