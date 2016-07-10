package com.dereekb.gae.model.extension.links.exception;


public class LinkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LinkException() {
		super();
	}

	public LinkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LinkException(String message, Throwable cause) {
		super(message, cause);
	}

	public LinkException(String message) {
		super(message);
	}

	public LinkException(Throwable cause) {
		super(cause);
	}

}
