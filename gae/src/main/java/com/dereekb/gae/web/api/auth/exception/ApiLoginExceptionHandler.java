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

	/**
	 * Used for login exceptions.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ApiLoginException.class)
	public ApiResponseImpl handleException(ApiLoginException exception) {
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
