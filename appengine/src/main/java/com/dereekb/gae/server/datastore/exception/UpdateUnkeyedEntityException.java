package com.dereekb.gae.server.datastore.exception;

/**
 * Thrown when an entity without an identifier does not save.
 * 
 * @author dereekb
 *
 */
public class UpdateUnkeyedEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UpdateUnkeyedEntityException() {
		super();
	}

	public UpdateUnkeyedEntityException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UpdateUnkeyedEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public UpdateUnkeyedEntityException(String message) {
		super(message);
	}

	public UpdateUnkeyedEntityException(Throwable cause) {
		super(cause);
	}

}
