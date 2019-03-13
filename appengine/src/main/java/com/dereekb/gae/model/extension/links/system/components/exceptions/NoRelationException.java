package com.dereekb.gae.model.extension.links.system.components.exceptions;

/**
 * Used by {@link LinkInfo} when the relation is not available.
 * 
 * @author dereekb
 *
 */
public class NoRelationException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRelationException() {
		super();
	}

	public NoRelationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoRelationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRelationException(String message) {
		super(message);
	}

	public NoRelationException(Throwable cause) {
		super(cause);
	}

}
