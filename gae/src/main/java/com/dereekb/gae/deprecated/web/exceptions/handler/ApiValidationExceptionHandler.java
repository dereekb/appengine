package com.thevisitcompany.gae.deprecated.web.exceptions.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.thevisitcompany.gae.deprecated.web.exceptions.validation.ValidationFieldIssue;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;

@ControllerAdvice
public class ApiValidationExceptionHandler {
	
	

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse handleException(MethodArgumentNotValidException exception) {
		ApiResponse response = new ApiResponse();

		BindingResult result = exception.getBindingResult();

		List<ValidationFieldIssue> issues = new ArrayList<ValidationFieldIssue>();
		List<FieldError> errors = result.getFieldErrors();

		Iterator<FieldError> iterator = errors.iterator();
		while (iterator.hasNext()) {
			FieldError error = iterator.next();
			ValidationFieldIssue issue = ValidationFieldIssue.withError(error);
			issues.add(issue);
		}

		response.putError(ApiResponse.VALIDATION_ERROR_KEY, issues);
		response.setSuccess(false);
		return response;
	}

}
