package com.thevisitcompany.gae.deprecated.web.exceptions;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;

public class ForbiddenModelRequestException extends ModelRequestException {

	private static final long serialVersionUID = 1L;

	private ForbiddenObjectChangesException exception;

	public ForbiddenModelRequestException(ApiResponse response) {
		super(response);
	}

	public ForbiddenModelRequestException(ForbiddenObjectChangesException e, ApiResponse response) {
		super(response);
		this.exception = e;
	}

	public ForbiddenObjectChangesException getException() {
		return exception;
	}

	public void setException(ForbiddenObjectChangesException exception) {
		this.exception = exception;
	}

}
