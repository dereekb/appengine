package com.dereekb.gae.web.api.model.exception;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when some resources are missing. Resources are described as strings,
 * and should be well described.
 *
 * @author dereekb
 *
 */
public class MissingRequiredResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String API_RESPONSE_ERROR_CODE = "MISSING_RESOURCE";
	public static final String API_RESPONSE_ERROR_TITLE = "Missing Resource";

	public List<String> resources;
	public String message;

	public MissingRequiredResourceException(List<String> resources) {
		this.resources = resources;
	}

	public MissingRequiredResourceException(List<String> resources, String message) {
		this.resources = resources;
		this.message = message;
	}

	public List<String> getResources() {
		return this.resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ApiResponseErrorImpl convertToResponse() {
		return makeApiError(this.resources, this.message);
	}

	public static ApiResponseErrorImpl makeApiError(Collection<String> resources,
	                                            String message) {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl();
		error.setCode(API_RESPONSE_ERROR_CODE);
		error.setTitle(API_RESPONSE_ERROR_TITLE);
		error.setDetail(message);
		error.setData(resources);
		return error;
	}

}
