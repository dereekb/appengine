package com.dereekb.gae.web.api.model.extension.search.exception;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Handles exceptions related to the search API.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
public class ApiSearchExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger(ApiSearchExceptionHandler.class.getName());

	/*
	@ResponseBody
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(DatastoreNeedIndexException.class)
	public ApiResponseImpl handleException(DatastoreNeedIndexException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		LOGGER.log(Level.WARNING, "Need Index Exception", exception);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("INDEX_UNAVAILABLE");
		error.setTitle("Index Unavailable");
		error.setDetail("The requested indexes for the submitted query are not available.");
		response.setError(error);

		return response;
	}
	*/

}
