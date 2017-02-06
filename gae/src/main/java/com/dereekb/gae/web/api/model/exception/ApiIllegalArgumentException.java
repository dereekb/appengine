package com.dereekb.gae.web.api.model.exception;

import com.dereekb.gae.web.api.model.exception.handler.ApiExceptionHandler;

/**
 * Used to wrap another {@link RuntimeException} encountered by the request.
 * <p>
 * Allows {@link ApiExceptionHandler} to catch this type and not all
 * {@link IllegalArgumentException} instances that occur.
 *
 * @author dereekb
 *
 */
public class ApiIllegalArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public IllegalArgumentException exception;

	public ApiIllegalArgumentException(IllegalArgumentException exception) {
		this.exception = exception;
	}

	public IllegalArgumentException getException() {
		return this.exception;
	}

	public void setException(IllegalArgumentException exception) {
		this.exception = exception;
	}

}
