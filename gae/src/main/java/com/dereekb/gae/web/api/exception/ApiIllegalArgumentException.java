package com.dereekb.gae.web.api.exception;

import com.dereekb.gae.web.api.exception.handler.ApiExceptionHandler;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Used to wrap another {@link RuntimeException} encountered by the request.
 * <p>
 * Allows {@link ApiExceptionHandler} to catch this type and not all
 * {@link IllegalArgumentException} instances that occur.
 *
 * @author dereekb
 *
 */
public class ApiIllegalArgumentException extends IllegalArgumentException
        implements ApiResponseErrorConvertable {

	public static final String ERROR_CODE = "BAD_ARG_EXCEPTION";
	public static final String ERROR_TITLE = "Bad Request Argument";

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

	// MARK: ApiResponseErrorConvertable
	@Override
	public ApiResponseError asResponseError() {
		String causeMessage = this.exception.getMessage();

		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE);
		error.setTitle(ERROR_TITLE);
		error.setDetail(causeMessage);

		return error;
	}

}
