package com.dereekb.gae.web.api.model.exception;


public class UnregisteredTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnregisteredTypeException() {
		super();
	}

	public UnregisteredTypeException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnregisteredTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnregisteredTypeException(String message) {
		super(message);
	}

	public UnregisteredTypeException(Throwable cause) {
		super(cause);
	}

}
