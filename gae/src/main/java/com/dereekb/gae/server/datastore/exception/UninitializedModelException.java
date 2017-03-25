package com.dereekb.gae.server.datastore.exception;

/**
 * {@link IllegalArgumentException} thrown when a model that requires an
 * identifier is provided.
 * 
 * @author dereekb
 *
 */
public class UninitializedModelException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public UninitializedModelException() {
		super();
	}

	public UninitializedModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public UninitializedModelException(String s) {
		super(s);
	}

	public UninitializedModelException(Throwable cause) {
		super(cause);
	}

}
