package com.dereekb.gae.web.api.model.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;

/**
 * {@link ControllerAdvice} for handling exceptions thrown by model related
 * requests.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
public class ModelRequestExceptionHandler {

	// GENERAL
	@ResponseBody
	@ResponseStatus(HttpStatus.GONE)
	@ExceptionHandler(MissingRequiredResourceException.class)
	public ApiResponse handleException(MissingRequiredResourceException exception) {
		ApiResponse response = new ApiResponse(false);

		ApiResponseError error = exception.convertToResponse();
		response.setError(error);

		return response;
	}

}
