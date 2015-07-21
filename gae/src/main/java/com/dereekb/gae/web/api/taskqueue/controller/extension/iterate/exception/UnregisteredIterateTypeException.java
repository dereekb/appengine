package com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.exception;


public class UnregisteredIterateTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnregisteredIterateTypeException() {
		super();
	}

	public UnregisteredIterateTypeException(String message) {
		super(message);
	}

	public UnregisteredIterateTypeException(Throwable cause) {
		super(cause);
	}

}
