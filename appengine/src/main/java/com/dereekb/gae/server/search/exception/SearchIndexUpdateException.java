package com.dereekb.gae.server.search.exception;

/**
 * General exception for when updating an index fails.
 *
 * @author dereekb
 *
 */
public class SearchIndexUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SearchIndexUpdateException() {
		super();
	}

	public SearchIndexUpdateException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SearchIndexUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

	public SearchIndexUpdateException(String message) {
		super(message);
	}

	public SearchIndexUpdateException(Throwable cause) {
		super(cause);
	}

}
