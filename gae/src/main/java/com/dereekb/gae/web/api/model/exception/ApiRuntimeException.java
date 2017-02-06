package com.dereekb.gae.web.api.model.exception;

import com.dereekb.gae.web.api.model.exception.handler.ApiExceptionHandler;

/**
 * Used to wrap another {@link RuntimeException} encountered by the request.
 * <p>
 * Allows {@link ApiExceptionHandler} to catch this type and not all
 * {@link RuntimeException} instances that occur.
 *
 * @author dereekb
 *
 */
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
