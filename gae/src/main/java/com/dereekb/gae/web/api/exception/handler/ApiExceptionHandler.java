package com.dereekb.gae.web.api.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.UnavailableException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.utilities.time.exception.RateLimitException;
import com.dereekb.gae.web.api.exception.WrappedApiBadRequestException;
import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.exception.WrappedApiUnprocessableEntityException;
import com.dereekb.gae.web.api.exception.ApiUnsupportedOperationException;
import com.dereekb.gae.web.api.exception.WrappedApiErrorException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Handles exceptions related to the API Requests, such as a request not being
 * parsable.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger(ApiExceptionHandler.class.getName());

	/**
	 * Used when the POST body cannot be parsed correctly.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ApiResponseImpl handleException(HttpMessageNotReadableException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		// TODO: Exposing this much info might not be as great as we'd hope..?

		Throwable cause = exception.getCause();
		String causeName = cause.getClass().getSimpleName();
		String causeMessage = cause.getMessage();

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("REQUEST_MESSAGE_EXCEPTION");
		error.setTitle(causeName);
		error.setDetail(causeMessage);
		response.setError(error);

		return response;
	}

	/**
	 * Used to catch various exceptions and return an error back to the user.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ApiIllegalArgumentException.class, WrappedApiBadRequestException.class,
	        WrappedApiErrorException.class })
	public ApiResponseImpl handleException(ApiResponseErrorConvertable e) {
		return ApiResponseImpl.makeFailure(e);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(WrappedApiUnprocessableEntityException.class)
	public ApiResponse handleException(WrappedApiUnprocessableEntityException exception) {
		return ApiResponseImpl.makeFailure(exception);
	}

	/**
	 * Used when an unexpected runtime exception occurs in a requested API
	 * process, but is not part of the request/response system.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApiCaughtRuntimeException.class)
	public ApiResponse handleException(ApiCaughtRuntimeException e) {
		RuntimeException exception = e.getException();

		// Log the error.
		LOGGER.log(Level.WARNING, "API Caught Runtime Error", exception);

		return ApiResponseImpl.makeFailure(e);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(ApiUnsupportedOperationException.class)
	public ApiResponse handleException(ApiUnsupportedOperationException e) {
		return ApiResponseImpl.makeFailure(e);
	}

	/**
	 * Used for any safe runtime exceptions that can explain themselves.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ApiSafeRuntimeException.class)
	public ApiResponse handleException(ApiSafeRuntimeException e) {
		return ApiResponseImpl.makeFailure(e);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(UnavailableException.class)
	public ApiResponse handleException(UnavailableException e) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("SERVICE_UNAVAILABLE");
		error.setTitle("Service Unavailable");
		error.setDetail("The service is currently unavailable due to an error.");

		response.setError(error);

		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ApiResponse handleException(HttpRequestMethodNotSupportedException e) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("METHOD_NOT_ALLOWED");
		error.setTitle("Method Not Allowed");
		error.setDetail(e.getMessage());

		response.setError(error);

		return response;
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
	@ExceptionHandler(RateLimitException.class)
	public ApiResponse handleException(RateLimitException e) {
		return ApiResponseImpl.makeFailure(e);
	}

	// MARK: Validation
	/**
	 * Used for capturing validation errors.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse handleException(MethodArgumentNotValidException e) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		BindingResult result = e.getBindingResult();
		ValidationError error = new ValidationError(result);
		response.setError(error);

		return response;
	}

	/**
	 * Used for capturing path variable exceptions.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingPathVariableException.class)
	public ApiResponse handleException(MissingPathVariableException e) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("MISSING_URL_VARIABLE");
		error.setTitle("Missing URL Variable");
		error.setDetail(e.getMessage());

		response.setError(error);

		return response;
	}

	/**
	 * Used for capturing missing request parameter exceptions.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ApiResponse handleException(MissingServletRequestParameterException e) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("MISSING_REQUEST_VARIABLE");
		error.setTitle("Missing Request Variable '" + e.getParameterName() + "'");
		error.setDetail(e.getMessage());

		response.setError(error);

		return response;
	}

	public static class ValidationError extends ApiResponseErrorImpl {

		private static final String ERROR_CODE = "VALIDATION_ERROR";
		private static final String ERROR_TITLE = "Validation Error";
		private static final String ERROR_DETAIL_FORMAT = "Error during data validation. Contains %s validation error(s).";

		public ValidationError() {
			super(ERROR_CODE, ERROR_TITLE);
		}

		public ValidationError(BindingResult bindingResult) {
			super(ERROR_CODE, ERROR_TITLE);
			this.buildUsingBindingResult(bindingResult);
		}

		public void buildUsingBindingResult(BindingResult bindingResult) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			List<FieldValidationIssue> issues = new ArrayList<>();

			for (FieldError error : errors) {
				FieldValidationIssue issue = new FieldValidationIssue(error);
				issues.add(issue);
			}

			super.setData(issues);
			this.setErrorCount(bindingResult.getErrorCount());
		}

		private void setErrorCount(Integer errorCount) {
			super.setDetail(String.format(ERROR_DETAIL_FORMAT, errorCount));
		}

	}

	@JsonInclude(Include.NON_EMPTY)
	public static class FieldValidationIssue {

		private String field;

		private Object value;

		private String message;

		public FieldValidationIssue() {}

		public FieldValidationIssue(FieldError error) {
			this.setField(error.getField());
			this.setValue(error.getRejectedValue());
			this.setMessage(error.getDefaultMessage());
		}

		public String getField() {
			return this.field;
		}

		public void setField(String field) {
			this.field = field;
		}

		@JsonInclude(Include.ALWAYS)
		public Object getValue() {
			return this.value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "FieldValidationIssue [field=" + this.field + ", value=" + this.value + ", message=" + this.message
			        + "]";
		}

	}

}
