package com.dereekb.gae.server.auth.security.token.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenException.TokenExceptionReason;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Handles exceptions related to login tokens.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiTokenExceptionHandler {

	@ResponseBody
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(TokenException.class)
	public ApiResponseImpl handleException(TokenException exception) {
		return this.buildErrorResponse(exception);
	}

	private ApiResponseImpl buildErrorResponse(TokenException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		TokenExceptionReason reason = exception.getReason();
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
