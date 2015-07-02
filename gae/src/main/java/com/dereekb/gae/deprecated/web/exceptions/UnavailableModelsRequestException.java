package com.thevisitcompany.gae.deprecated.web.exceptions;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;

/**
 * Thrown when the entity is missing or unavailable due to non-permission reasons.
 * 
 * @author dereekb
 */
public class UnavailableModelsRequestException extends ModelRequestException {

	private static final long serialVersionUID = 1L;

	private UnavailableObjectsException exception;

	public UnavailableModelsRequestException(ApiResponse response) {
		super(response);
	}

	public UnavailableModelsRequestException(UnavailableObjectsException exception, ApiResponse response) {
		super(response);
		this.exception = exception;
	}

	public UnavailableObjectsException getException() {
		return exception;
	}

	public void setException(UnavailableObjectsException exception) {
		this.exception = exception;
	}

}
