package com.dereekb.gae.utilities.data.exception;


public class InvalidBytesException extends RuntimeException {

	public InvalidBytesException() {
		super();
	}

	public InvalidBytesException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidBytesException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidBytesException(String message) {
		super(message);
	}

	public InvalidBytesException(Throwable cause) {
		super(cause);
	}

}
