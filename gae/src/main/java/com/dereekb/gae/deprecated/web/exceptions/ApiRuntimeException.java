package com.thevisitcompany.gae.deprecated.web.exceptions;

public final class ApiRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RuntimeException exception;

	public ApiRuntimeException(RuntimeException exception) {
		this.exception = exception;
	}

	public RuntimeException getException() {
		return this.exception;
	}

	public void setException(RuntimeException exception) {
		this.exception = exception;
	}

}
