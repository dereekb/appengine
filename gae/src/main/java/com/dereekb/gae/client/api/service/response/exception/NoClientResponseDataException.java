package com.dereekb.gae.client.api.service.response.exception;

public class NoClientResponseDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoClientResponseDataException() {
		super();
	}

	public NoClientResponseDataException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoClientResponseDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoClientResponseDataException(String message) {
		super(message);
	}

	public NoClientResponseDataException(Throwable cause) {
		super(cause);
	}

}
