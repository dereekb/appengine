package com.dereekb.gae.web.api.model.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.web.api.model.crud.exception.NoTemplateDataExeption;
import com.dereekb.gae.web.api.model.exception.MissingRequiredResourceException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.MultiKeyedInvalidAttributeException;

/**
 * {@link ControllerAdvice} for handling exceptions thrown by model related
 * requests.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ModelRequestExceptionHandler {

	// MARK: Atomic Operations
	@ResponseBody
	@ResponseStatus(HttpStatus.GONE)
	@ExceptionHandler(MissingRequiredResourceException.class)
	public ApiResponse handleException(MissingRequiredResourceException exception) {
		return ApiResponseImpl.makeFailure(exception);
	}

	// MARK: Templates and Data
	@ResponseBody
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(NoTemplateDataExeption.class)
	public ApiResponse handleException(NoTemplateDataExeption exception) {
		return ApiResponseImpl.makeFailure(exception);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(KeyedInvalidAttributeException.class)
	public ApiResponse handleException(KeyedInvalidAttributeException exception) {
		return ApiResponseImpl.makeFailure(exception);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MultiKeyedInvalidAttributeException.class)
	public ApiResponse handleException(MultiKeyedInvalidAttributeException exception) {
		return ApiResponseImpl.makeFailure(exception);
	}

}
