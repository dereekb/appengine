package com.dereekb.gae.web.api.model.extension.link.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangeException;
import com.dereekb.gae.model.extension.links.service.exception.LinkSystemChangesException;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Handles exceptions related to the Login API Requests.
 *
 * @author dereekb
 *
 */
@ControllerAdvice
public class ApiLinkExceptionHandler {

	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(LinkSystemChangesException.class)
	public ApiResponseImpl handleException(LinkSystemChangesException exception) {
		ApiResponseImpl response = new ApiResponseImpl(false);

		List<LinkSystemChangeException> changes = exception.getExceptions();

		for (LinkSystemChangeException changeException : changes) {
			ApiResponseError error = changeException.getResponseError();
			response.addError(error);
		}

		return response;
	}

}
