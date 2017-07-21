package com.dereekb.gae.model.extension.links.system.modification.exception;

/**
 * Thrown in cases where there is no undo action to perform.
 * 
 * @author dereekb
 *
 */
public class NoUndoChangesException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoUndoChangesException() {
		super();
	}

	public NoUndoChangesException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoUndoChangesException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoUndoChangesException(String message) {
		super(message);
	}

	public NoUndoChangesException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "NoUndoChangesException []";
	}

}
