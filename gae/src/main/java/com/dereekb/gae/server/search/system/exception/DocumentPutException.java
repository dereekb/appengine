package com.dereekb.gae.server.search.system.exception;

/**
 * General exception for when put fails.
 *
 * @author dereekb
 *
 */
public class DocumentPutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DocumentPutException() {
		super();
	}

	public DocumentPutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DocumentPutException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentPutException(String message) {
		super(message);
	}

	public DocumentPutException(Throwable cause) {
		super(cause);
	}

}
