package com.dereekb.gae.model.extension.links.system.modification.exception.internal;

/**
 * Thrown in cases where changes have already been performed.
 * 
 * @author dereekb
 *
 */
public class ChangesAlreadyExecutedException extends InternalLinkModificationSystemException {

	private static final long serialVersionUID = 1L;

	public ChangesAlreadyExecutedException() {
		super();
	}

	public ChangesAlreadyExecutedException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ChangesAlreadyExecutedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChangesAlreadyExecutedException(String message) {
		super(message);
	}

	public ChangesAlreadyExecutedException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "ChangesAlreadyExecutedException []";
	}

}
