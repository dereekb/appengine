package com.dereekb.gae.web.api.model.exception;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.exception.ApiResponseErrorConvertable;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Thrown when some resources are missing. Resources are keyed as strings.
 *
 * @author dereekb
 *
 */
public class MissingRequiredResourceException extends RuntimeException
        implements ApiResponseErrorConvertable {

	private static final long serialVersionUID = 1L;

	public static final String API_RESPONSE_ERROR_CODE = "MISSING_RESOURCE";
	public static final String API_RESPONSE_ERROR_TITLE = "Missing Resource";

	public List<String> resources;
	public String message;

	public MissingRequiredResourceException(List<String> resources) {
		this(resources, null);
	}

	public MissingRequiredResourceException(List<String> resources, String message) {
		this.setResources(resources);
		this.setMessage(message);
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

	// MARK: ApiResponseErrorConvertable
	@Override
	public ApiResponseErrorImpl asResponseError() {
		return makeApiError(this.resources, this.message);
	}

	/**
	 * Attempts to create an api response.
	 * 
	 * @param resourceKeys
	 *            {@link Collection}. Never {@code null}.
	 * @param message
	 *            Error message.
	 * @return {@link ApiResponseErrorImpl}, or {@code null} if the input
	 *         collection is empty.
	 */
	public static ApiResponseErrorImpl tryMakeApiErrorForModelKeys(Collection<ModelKey> resourceKeys,
	                                                               String message) {
		ApiResponseErrorImpl error = null;

		if (resourceKeys.isEmpty() == false) {
			List<String> keys = ModelKey.readStringKeys(resourceKeys);
			error = makeApiError(keys, message);
		}

		return error;
	}

	public static ApiResponseErrorImpl makeApiError(Collection<String> resources,
	                                                String message) {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(API_RESPONSE_ERROR_CODE);
		error.setTitle(API_RESPONSE_ERROR_TITLE);
		error.setDetail(message);
		error.setData(resources);
		return error;
	}

}
