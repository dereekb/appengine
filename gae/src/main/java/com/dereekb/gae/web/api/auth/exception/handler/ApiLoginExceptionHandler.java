package com.dereekb.gae.web.api.auth.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginErrorException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginExistsException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginInvalidException;
import com.dereekb.gae.web.api.auth.exception.ApiLoginRejectedException;
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
public class ApiLoginExceptionHandler {

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ApiLoginException.class)
	public ApiResponseImpl handleException(ApiLoginException exception) {
		return this.buildErrorResponse(exception);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApiLoginErrorException.class)
	public ApiResponseImpl handleErrorException(ApiLoginErrorException exception) {
		return this.buildErrorResponse(exception);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(ApiLoginInvalidException.class)
	public ApiResponseImpl handleInvalidException(ApiLoginInvalidException exception) {
		return this.buildErrorResponse(exception);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(ApiLoginRejectedException.class)
	public ApiResponseImpl handleInvalidException(ApiLoginRejectedException exception) {
		return this.buildErrorResponse(exception);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(ApiLoginExistsException.class)
	public ApiResponseImpl handleExistsException(ApiLoginExistsException exception) {
		return this.buildErrorResponse(exception);
	}

	private ApiResponseImpl buildErrorResponse(ApiLoginException exception) {
		return ApiResponseImpl.makeFailure(exception);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(NoSecurityContextException.class)
	public ApiResponseImpl handleException(NoSecurityContextException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("MISSING_SECURITY_CONTEXT");
		error.setTitle("Security context failure");

		response.setError(error);

		return response;
	}

}
