package com.dereekb.gae.server.search.service.exception;


/**
 * Thrown when a {@link Document} does not exist.
 *
 * @author dereekb
 *
 */
public class MissingDocumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingDocumentException() {
		super();
	}

	public MissingDocumentException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MissingDocumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingDocumentException(String message) {
		super(message);
	}

	public MissingDocumentException(Throwable cause) {
		super(cause);
	}

}
