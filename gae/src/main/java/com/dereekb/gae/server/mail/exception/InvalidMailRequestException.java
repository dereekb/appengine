package com.dereekb.gae.server.mail.exception;

/**
 * Throw when the input request is invalid.
 * 
 * @author dereekb
 *
 */
public class InvalidMailRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidMailRequestException() {
		super();
	}

	public InvalidMailRequestException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidMailRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMailRequestException(String message) {
		super(message);
	}

	public InvalidMailRequestException(Throwable cause) {
		super(cause);
	}

}
