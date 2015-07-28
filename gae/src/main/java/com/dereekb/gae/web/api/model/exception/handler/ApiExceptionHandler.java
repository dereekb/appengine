package com.dereekb.gae.web.api.model.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Handles exceptions related to the API Requests, such as a request not being
 * parsable.
 *
 * @author dereekb
 *
 */
public class ApiExceptionHandler {

	/**
	 * Used when the POST body cannot be parsed correctly.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ApiResponseImpl handleException(HttpMessageNotReadableException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		Throwable cause = exception.getCause();
		String causeName = cause.getClass().getName();
		String causeMessage = cause.getMessage();

		ApiResponseErrorImpl error = new ApiResponseErrorImpl();
		error.setCode("REQUEST_READ_EXCEPTION");
		error.setTitle(causeName);
		error.setDetail(causeMessage);

		response.setError(error);

		return response;
	}

	/**
	 * Used when an unexpected runtime exception occurs in a requested API
	 * process, but is not part of the request/response system.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApiRuntimeException.class)
	public ApiResponse handleException(ApiRuntimeException e) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		RuntimeException exception = e.getException();

		ApiResponseErrorImpl error = new ApiResponseErrorImpl();
		error.setCode("SERVER_EXCEPTION");
		error.setTitle(exception.getClass().getSimpleName());
		error.setDetail(exception.getMessage());

		response.setError(error);

		exception.printStackTrace();

		return response;
	}

}
