package com.dereekb.gae.web.api.model.extension.search.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.google.appengine.api.datastore.DatastoreNeedIndexException;

/**
 * Handles exceptions related to the search API.
 * 
 * @author dereekb
 *
 */
@ControllerAdvice
public class ApiSearchExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger(ApiSearchExceptionHandler.class.getName());

	@ResponseBody
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(DatastoreNeedIndexException.class)
	public ApiResponseImpl handleException(DatastoreNeedIndexException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		LOGGER.log(Level.WARNING, "Need Index Exception", exception);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("INDEX_UNAVAILABLE");
		error.setTitle("Index Unavailable");
		error.setDetail("The requested indexes for the submitted query are not available.");

		return response;
	}

}
