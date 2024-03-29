package com.dereekb.gae.server.search.exception;


/**
 * Thrown when a {@link SearchServiceBatchIterator} reaches the end of all values.
 *
 * @author dereekb
 *
 */
public class DocumentIterationBoundsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DocumentIterationBoundsException() {
		super();
	}

	public DocumentIterationBoundsException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DocumentIterationBoundsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentIterationBoundsException(String message) {
		super(message);
	}

	public DocumentIterationBoundsException(Throwable cause) {
		super(cause);
	}

}
