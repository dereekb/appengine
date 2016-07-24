package com.dereekb.gae.web.api.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.web.api.auth.exception.ApiLoginException.LoginExceptionReason;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Handles exceptions related to the Login API Requests.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
public class ApiLoginExceptionHandler {

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ApiLoginException.class)
	public ApiResponseImpl handleException(ApiLoginException exception) {
		return this.buildErrorResponse(exception);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApiLoginErrorException.class)
	public ApiResponseImpl handleErrorException(ApiLoginErrorException exception) {
		return this.buildErrorResponse(exception);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(ApiLoginInvalidException.class)
	public ApiResponseImpl handleInvalidException(ApiLoginInvalidException exception) {
		return this.buildErrorResponse(exception);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(ApiLoginExistsException.class)
	public ApiResponseImpl handleExistsException(ApiLoginExistsException exception) {
		return this.buildErrorResponse(exception);
	}

	private ApiResponseImpl buildErrorResponse(ApiLoginException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		LoginExceptionReason reason = exception.getReason();
		ApiResponseErrorImpl error = reason.makeResponseError();

		String message = exception.getMessage();
		Throwable cause = exception.getCause();

		if (cause != null) {
			message = cause.getMessage();
		}

		error.setDetail(message);
		response.setError(error);

		return response;
	}

}
