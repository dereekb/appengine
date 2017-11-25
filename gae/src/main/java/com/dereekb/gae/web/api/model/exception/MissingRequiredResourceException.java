package com.dereekb.gae.web.api.model.exception;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.exception.ApiSafeRuntimeException;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Thrown when some resources are missing. Resources are keyed as strings.
 *
 * @author dereekb
 *
 */
public class MissingRequiredResourceException extends ApiSafeRuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "MISSING_RESOURCE";
	public static final String ERROR_TITLE = "Missing Resource";

	public static final String ERROR_DATA_TYPE_CODE = "type";
	public static final String ERROR_DATA_KEYS_CODE = "resources";

	private String type;
	private List<String> resources;
	private String message;

	public MissingRequiredResourceException(List<String> resources) {
		this(null, resources);
	}

	public MissingRequiredResourceException(String type, List<String> resources) {
		this(type, resources, null);
	}

	public MissingRequiredResourceException(List<String> resources, String message) {
		this(null, resources, message);
	}

	public MissingRequiredResourceException(String type, List<String> resources, String message) {
		this.setType(type);
		this.setResources(resources);
		this.setMessage(message);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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
		return makeApiError(null, resources, message);
	}

	public static ApiResponseErrorImpl makeApiError(String type,
	                                                Collection<String> resources,
	                                                String message) {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
		error.setDetail(message);

		MissingRequiredResourceExceptionData data = new MissingRequiredResourceExceptionData(type, resources);
		error.setData(data);

		return error;
	}

	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MissingRequiredResourceExceptionData {

		private String type;
		private Collection<String> resources;

		public MissingRequiredResourceExceptionData(String type, Collection<String> resources) {
			super();
			this.type = type;
			this.resources = resources;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Collection<String> getResources() {
			return this.resources;
		}

		public void setResources(Collection<String> resources) {
			this.resources = resources;
		}

	}

}
