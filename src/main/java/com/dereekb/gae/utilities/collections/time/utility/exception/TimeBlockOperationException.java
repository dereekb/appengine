package com.dereekb.gae.utilities.collections.time.utility.exception;

/**
 * Thrown by TimeBlock operator/operations.
 * 
 * @author dereekb
 *
 */
public class TimeBlockOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TimeBlockOperationException() {
		super();
	}
	
	public TimeBlockOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeBlockOperationException(String message) {
		super(message);
	}

	public TimeBlockOperationException(Throwable cause) {
		super(cause);
	}

}
