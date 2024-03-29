package com.dereekb.gae.web.api.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.UnavailableException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.utilities.time.exception.RateLimitException;
import com.dereekb.gae.web.api.exception.ApiCaughtRuntimeException;
import com.dereekb.gae.web.api.exception.ApiIllegalArgumentException;
import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.exception.ApiUnsupportedOperationException;
import com.dereekb.gae.web.api.exception.WrappedApiBadRequestException;
import com.dereekb.gae.web.api.exception.WrappedApiErrorException;
import com.dereekb.gae.web.api.exception.WrappedApiNotFoundException;
import com.dereekb.gae.web.api.exception.WrappedApiUnprocessableEntityException;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonMappingException;

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

		// Log the error.
		LOGGER.log(Level.WARNING, "HTTP Message Read Exception", exception);

		String title = "Request Message Exception";

		Throwable cause = exception.getCause();

		if (cause != null) {
			String causeName = cause.getClass().getSimpleName();
			title = title + " (" + causeName + ")";
		}

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("REQUEST_MESSAGE_EXCEPTION");
		error.setTitle(title);
		error.setDetail("The server failed to completely parse the request message.");

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
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ WrappedApiNotFoundException.class })
	public ApiResponseImpl handleException(WrappedApiNotFoundException e) {
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
		LOGGER.log(Level.WARNING, "API Caught API Safe Runtime Error", e);
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
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ApiResponse handleException(HttpMediaTypeNotSupportedException e) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("UNSUPPORTED_MEDIA_TYPE");
		error.setTitle("Unsupported Media Type");
		error.setDetail("Change the media type of your request to a supported type.");

		error.setData(new HttpMediaTypeNotSupportedData(e));

		response.setError(error);

		return response;
	}

	public static class HttpMediaTypeNotSupportedData {

		private String requestContentType;
		private List<String> supportedTypes;

		public HttpMediaTypeNotSupportedData(HttpMediaTypeNotSupportedException e) {

			List<String> supportedTypes = new ArrayList<String>();
			for (MediaType type : e.getSupportedMediaTypes()) {
				supportedTypes.add(type.toString());
			}

			this.supportedTypes = supportedTypes;

			MediaType type = e.getContentType();

			if (type != null) {
				this.requestContentType = type.toString();
			}
		}

		public String getRequestContentType() {
			return this.requestContentType;
		}

		public List<String> getSupportedTypes() {
			return this.supportedTypes;
		}

	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
	@ExceptionHandler(RateLimitException.class)
	public ApiResponse handleException(RateLimitException e) {
		return ApiResponseImpl.makeFailure(e);
	}

	// MARK: Serialization
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(JsonMappingException.class)
	public ApiResponse handleException(JsonMappingException e) {

		// Log the error.
		LOGGER.log(Level.WARNING, "Json Mapping Error", e);

		ApiResponseImpl response = new ApiResponseImpl(false);

		ApiResponseErrorImpl error = new ApiResponseErrorImpl("JSON_MAPPING_ERROR");
		error.setTitle("Json Mapping Error");
		error.setDetail("An error occured while mapping JSON.");

		response.setError(error);

		return response;
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
	 * Used for capturing validation errors.
	 */
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ApiResponse handleException(ConstraintViolationException e) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintError error = new ConstraintError(violations);
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

	public static class ConstraintError extends ApiResponseErrorImpl {

		private static final String ERROR_CODE = "REQUEST_ERROR";
		private static final String ERROR_TITLE = "Request Error";
		private static final String ERROR_DETAIL_FORMAT = "Invalid Request. Contains %s request error(s).";

		public ConstraintError() {
			super(ERROR_CODE, ERROR_TITLE);
		}

		public ConstraintError(Set<ConstraintViolation<?>> violations) {
			this();
			this.buildUsingViolations(violations);
		}

		private void buildUsingViolations(Set<ConstraintViolation<?>> violations) {
			List<FieldValidationIssue> issues = new ArrayList<>();

			for (ConstraintViolation<?> violation : violations) {
				FieldValidationIssue issue = new FieldValidationIssue();

				issue.setField("n/a");
				issue.setValue("n/a");
				issue.setMessage(violation.getMessage());

				issues.add(issue);
			}

			super.setData(issues);
			this.setErrorCount(violations.size());
		}

		private void setErrorCount(Integer errorCount) {
			super.setDetail(String.format(ERROR_DETAIL_FORMAT, errorCount));
		}

	}

	public static class ValidationError extends ApiResponseErrorImpl {

		private static final String ERROR_CODE = "VALIDATION_ERROR";
		private static final String ERROR_TITLE = "Validation Error";
		private static final String ERROR_DETAIL_FORMAT = "Error during data validation. Contains %s validation error(s).";

		public ValidationError() {
			super(ERROR_CODE, ERROR_TITLE);
		}

		public ValidationError(BindingResult bindingResult) {
			this();
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
