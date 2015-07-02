package com.thevisitcompany.gae.deprecated.web.exceptions;


public class ModelNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ModelNotFoundException() {
		super();
	}

	public ModelNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelNotFoundException(String message) {
		super(message);
	}

	public ModelNotFoundException(Throwable cause) {
		super(cause);
	}
}