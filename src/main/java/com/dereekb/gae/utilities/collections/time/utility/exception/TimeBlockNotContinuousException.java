package com.dereekb.gae.utilities.collections.time.utility.exception;

/**
 * Thrown when two time blocks are not continuous.
 * 
 * @author dereekb
 *
 */
public class TimeBlockNotContinuousException extends TimeBlockOperationException {

	private static final long serialVersionUID = 1L;

	public TimeBlockNotContinuousException() {
		super();
	}

	public TimeBlockNotContinuousException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeBlockNotContinuousException(String message) {
		super(message);
	}

	public TimeBlockNotContinuousException(Throwable cause) {
		super(cause);
	}

}
