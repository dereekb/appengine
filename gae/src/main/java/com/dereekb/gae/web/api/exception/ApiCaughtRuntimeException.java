package com.dereekb.gae.web.api.exception;

import com.dereekb.gae.web.api.exception.handler.ApiExceptionHandler;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Used to wrap another {@link RuntimeException} encountered by the request.
 * <p>
 * Allows the {@link ApiExceptionHandler} to catch this type and not all
 * {@link RuntimeException} instances that occur.
 *
 * @author dereekb
 *
 */
public final class ApiCaughtRuntimeException extends RuntimeException
        implements ApiResponseErrorConvertable {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "SERVER_EXCEPTION";
	public static final String ERROR_TITLE = "Server Exception";

	public RuntimeException exception;

	private ApiCaughtRuntimeException(RuntimeException exception) {
		this.exception = exception;
	}

	public static ApiCaughtRuntimeException make(RuntimeException exception) {
		if (exception == null) {
			throw new IllegalArgumentException("Exception cannot be null.");
		}

		return new ApiCaughtRuntimeException(exception);
	}

	public RuntimeException getException() {
		return this.exception;
	}

	// MARK: ApiResponseErrorConvertable
	@Override
	public ApiResponseErrorImpl asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
		
		error.setDetail("Something went wrong.");

		/*
		 * error.setTitle(this.exception.getClass().getSimpleName());
		 * error.setDetail(this.exception.getMessage());
		 */

		return error;
	}

}
