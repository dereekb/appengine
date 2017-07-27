package com.dereekb.gae.model.extension.links.system.modification.exception.internal;

/**
 * Thrown in cases where undo changes have already been performed.
 * 
 * @author dereekb
 *
 */
public class UndoChangesAlreadyExecutedException extends InternalLinkModificationSystemException {

	private static final long serialVersionUID = 1L;

	public UndoChangesAlreadyExecutedException() {
		super();
	}

	public UndoChangesAlreadyExecutedException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UndoChangesAlreadyExecutedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UndoChangesAlreadyExecutedException(String message) {
		super(message);
	}

	public UndoChangesAlreadyExecutedException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "ChangesAlreadyExecutedException []";
	}

}
