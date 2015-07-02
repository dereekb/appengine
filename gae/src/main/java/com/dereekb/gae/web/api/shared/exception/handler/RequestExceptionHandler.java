package com.dereekb.gae.web.api.shared.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public final class RequestExceptionHandler {

	/*
	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(RequestRuntimeException.class)
	public ApiResponse handleException( exception) {
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
	*/
	
}
