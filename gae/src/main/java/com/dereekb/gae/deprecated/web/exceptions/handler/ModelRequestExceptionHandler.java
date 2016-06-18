package com.thevisitcompany.gae.deprecated.web.exceptions.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.web.api.model.exception.ApiRuntimeException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;

@ControllerAdvice
public class ModelRequestExceptionHandler {

	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenModelRequestException.class)
	public ApiResponse handleException(ForbiddenModelRequestException exception) {
		ApiResponse response = exception.getResponse();
		ForbiddenObjectChangesException e = exception.getException();

		response.putError(ApiResponse.ERROR_EXCEPTION_KEY, "Forbidden Request");
		if (e != null) {
			response.putError(ApiResponse.ERROR_REASON_KEY, e.getReason());
			response.putError(ApiResponse.ERROR_MESSAGE_KEY, e.getMessage());
			response.putError("identifiers", e.getIdentifiers());
		}

		return response;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.GONE)
	@ExceptionHandler(UnavailableModelsRequestException.class)
	public ApiResponse handleException(UnavailableModelsRequestException exception) {
		ApiResponse response = exception.getResponse();
		UnavailableObjectsException e = exception.getException();
		response.putError(ApiResponse.ERROR_EXCEPTION_KEY, "Requested Models Unavailable");

		if (e != null) {
			List<Object> unavailableIds = e.getUnavailableList();
			List<Object> availableIds = e.getFoundList();
			response.putError(ApiResponse.ERROR_REASON_KEY, e.getReason());
			response.putError(ApiResponse.ERROR_MESSAGE_KEY, e.getMessage());
			response.putError("unavailable", unavailableIds);
			response.putError("available", availableIds);
		}

		response.setSuccess(false);
		return response;
	}

	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Unsupported Request Type")
	@ExceptionHandler(UnsupportedRequestException.class)
	public void handleException(UnsupportedRequestException exception) {}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.GONE)
	@ExceptionHandler(ResourceUnavailableException.class)
	public ApiResponse handleException(ResourceUnavailableException exception) {
		ApiResponse response = new ApiResponse();

		response.putError(ApiResponse.ERROR_EXCEPTION_KEY, "Resource Unavailable");
		response.putError(ApiResponse.ERROR_MESSAGE_KEY, exception.getMessage());

		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ApiTaskUnavailableException.class)
	public ApiResponse handleException(ApiTaskUnavailableException exception) {
		ApiResponse response = new ApiResponse();

		response.putError(ApiResponse.ERROR_EXCEPTION_KEY, "Task Unavailable");
		response.putError(ApiResponse.ERROR_MESSAGE_KEY, exception.getMessage());

		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ApiLinksChangeUnavailableException.class)
	public ApiResponse handleException(ApiLinksChangeUnavailableException exception) {
		ApiResponse response = new ApiResponse();

		response.putError(ApiResponse.ERROR_EXCEPTION_KEY, "Link Unavailable");
		response.putError(ApiResponse.ERROR_MESSAGE_KEY, exception.getMessage());

		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApiRuntimeException.class)
	public ApiResponse handleException(ApiRuntimeException exception) {
		ApiResponse response = new ApiResponse();

		RuntimeException e = exception.getException();
		String exceptionName = e.getClass().getSimpleName();
		String message = e.getMessage();

		e.printStackTrace();

		response.putError(ApiResponse.ERROR_EXCEPTION_KEY, exceptionName);
		response.putError(ApiResponse.ERROR_MESSAGE_KEY, message);
		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ModelRequestException.class)
	public ApiResponse handleException(ModelRequestException exception) {
		ApiResponse response = exception.getResponse();

		String message = exception.getMessage();

		response.putError(ApiResponse.ERROR_EXCEPTION_KEY, "Request Exception");
		response.putError(ApiResponse.ERROR_MESSAGE_KEY, message);
		response.setSuccess(false);

		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ApiResponse handleException(HttpMessageNotReadableException exception) {
		ApiResponse response = new ApiResponse();

		Throwable cause = exception.getCause();
		String exceptionName = cause.getClass().getName();
		String message = cause.getMessage();

		response.putError(ApiResponse.ERROR_EXCEPTION_KEY, exceptionName);
		response.putError(ApiResponse.ERROR_MESSAGE_KEY, message);
		response.setSuccess(false);

		return response;
	}

	/*
	 * @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	 * @ExceptionHandler(RuntimeException.class)
	 * public void handleRuntimeException(RuntimeException exception) {
	 * exception.printStackTrace();
	 * }
	 */
}