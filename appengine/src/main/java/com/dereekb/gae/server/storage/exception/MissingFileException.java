package com.dereekb.gae.server.storage.exception;

/**
 * Generic exception to throw for missing files.
 * 
 * @author dereekb
 */
public class MissingFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingFileException() {
		super();
	}

	public MissingFileException(String message) {
		super(message);
	}

	public MissingFileException(Throwable cause) {
		super(cause);
	}

	public MissingFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
