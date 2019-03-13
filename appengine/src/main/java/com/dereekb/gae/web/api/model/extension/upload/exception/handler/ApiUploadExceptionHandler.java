package com.dereekb.gae.web.api.model.extension.upload.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.server.storage.upload.exception.FileUploadFailedException;
import com.dereekb.gae.server.storage.upload.exception.FileUploadUrlCreationException;
import com.dereekb.gae.web.api.model.extension.upload.exception.InvalidUploadTypeException;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Handles exceptions related to the Login API Requests.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiUploadExceptionHandler {

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FileUploadUrlCreationException.class)
	public ApiResponseImpl handleException(FileUploadUrlCreationException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);
		ApiResponseErrorImpl error = new ApiResponseErrorImpl("UPLOAD_URL_EXCEPTION");

		error.setTitle("URL Creation Exception");
		error.setData(exception.getMessage());

		response.setError(error);
		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidUploadTypeException.class)
	public ApiResponseImpl handleException(InvalidUploadTypeException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);
		ApiResponseErrorImpl error = new ApiResponseErrorImpl("INVALID_UPLOAD_TYPE");

		error.setTitle("Invalid Upload Type");
		error.setData(exception.getType());

		response.setError(error);
		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FileUploadFailedException.class)
	public ApiResponseImpl handleException(FileUploadFailedException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);
		ApiResponseErrorImpl error = new ApiResponseErrorImpl("FAILED_UPLOAD");

		error.setTitle("Failed Upload");
		error.setDetail(exception.getMessage());

		response.setError(error);
		return response;
	}

}
