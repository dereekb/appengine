package com.dereekb.gae.model.extension.links.system.modification.exception;

/**
 * Thrown in cases where changes have already been performed.
 * 
 * @author dereekb
 *
 */
public class ChangesAlreadyComittedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ChangesAlreadyComittedException() {
		super();
	}

	public ChangesAlreadyComittedException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ChangesAlreadyComittedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChangesAlreadyComittedException(String message) {
		super(message);
	}

	public ChangesAlreadyComittedException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "ChangesAlreadyExecutedException []";
	}

}
