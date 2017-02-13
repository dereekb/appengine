package com.dereekb.gae.web.api.model.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.web.api.model.controller.exception.NoTemplateDataExeption;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.dereekb.gae.web.api.util.attribute.builder.KeyedAttributeUpdateFailureApiResponseBuilder;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedAttributeUpdateFailureException;

/**
 * {@link ControllerAdvice} for handling exceptions thrown by model related
 * requests.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
public class ModelRequestExceptionHandler {

	// MARK: Atomic Operations
	@ResponseBody
	@ResponseStatus(HttpStatus.GONE)
	@ExceptionHandler(MissingRequiredResourceException.class)
	public ApiResponse handleException(MissingRequiredResourceException exception) {
		return ApiResponseImpl.makeFailure(exception);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(NoTemplateDataExeption.class)
	public ApiResponse handleException(NoTemplateDataExeption exception) {
		return ApiResponseImpl.makeFailure(exception);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(KeyedAttributeUpdateFailureException.class)
	public ApiResponse handleException(KeyedAttributeUpdateFailureException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = KeyedAttributeUpdateFailureApiResponseBuilder.make(exception);
		response.setError(error);

		return response;
	}

}
