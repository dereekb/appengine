package com.dereekb.gae.utilities.collections.time.utility.exception;

/**
 * Thrown when no overlap exists.
 * 
 * @author dereekb
 *
 */
public class TimeBlockNoOverlapException extends TimeBlockOperationException {

	private static final long serialVersionUID = 1L;

	public TimeBlockNoOverlapException() {
		super();
	}
	
	public TimeBlockNoOverlapException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeBlockNoOverlapException(String message) {
		super(message);
	}

	public TimeBlockNoOverlapException(Throwable cause) {
		super(cause);
	}

}
