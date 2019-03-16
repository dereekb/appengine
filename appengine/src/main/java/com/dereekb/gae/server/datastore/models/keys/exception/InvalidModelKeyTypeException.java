package com.dereekb.gae.server.datastore.models.keys.exception;

/**
 * Thrown when the expected model key type is not allowed.
 * 
 * @author dereekb
 *
 */
public class InvalidModelKeyTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidModelKeyTypeException() {
		super();
	}

	public InvalidModelKeyTypeException(String s) {
		super(s);
	}

	public InvalidModelKeyTypeException(Throwable cause) {
		super(cause);
	}

}
