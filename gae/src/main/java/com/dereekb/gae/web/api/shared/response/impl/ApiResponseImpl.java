package com.dereekb.gae.web.api.shared.response.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Wrapper for a server response.
 * <p>
 * Uses {@link ApiResponseData} and {@link ApiResponseError}.
 *
 * @author dereekb
 * @see <a href="http://jsonapi.org/format/">JSON API</a>
 */
@JsonInclude(Include.NON_EMPTY)
public class ApiResponseImpl
        implements ApiResponse {

	/**
	 * Whether or not the processed request was successful.
	 */
	@JsonInclude(Include.NON_DEFAULT)
	protected Boolean success = true;

	/**
	 * Primary data type.
	 */
	private ApiResponseData data;

	/**
	 * Secondary types that are related resources.
	 */
	private Map<String, ApiResponseData> included;

	/**
	 * List of errors, if any are available.
	 */
	private List<ApiResponseError> errors = new ArrayList<ApiResponseError>();

	public ApiResponseImpl() {}

	public ApiResponseImpl(boolean success) {
		this.success = success;
	}

	public Boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	// Data
	public ApiResponseData getData() {
		return this.data;
	}

	public void setData(ApiResponseData data) {
		this.data = data;
	}

	// Included
	public void initializeIncluded() {
		if (this.included == null) {
			this.included = new HashMap<String, ApiResponseData>();
		}
	}

	public Map<String, ApiResponseData> getIncluded() {
		this.initializeIncluded();
		return this.included;
	}

	public void setIncluded(Map<String, ApiResponseData> included) {
		this.included = included;
	}

	public void addIncluded(ApiResponseData data) {
		this.initializeIncluded();

		String type = data.getResponseDataType();
		this.included.put(type, data);
	}

	// Errors
	public void initializeErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<ApiResponseError>();
		}
	}

	public List<ApiResponseError> getErrors() {
		this.initializeErrors();
		return this.errors;
	}

	public void setError(ApiResponseError error) {
		this.errors = null;
		this.addError(error);
	}

	public void setErrors(Collection<ApiResponseError> errors) {
		this.errors = null;
		this.addErrors(errors);
	}

	public void addError(ApiResponseError error) {
		this.initializeErrors();
		this.errors.add(error);
	}

	public void addErrors(Collection<ApiResponseError> errors) {
		this.initializeErrors();
		this.errors.addAll(errors);
	}

	@Override
	public String toString() {
		return "ApiResponseImpl [success=" + this.success + ", data=" + this.data + ", included=" + this.included
		        + ", errors=" + this.errors + "]";
	}

	// MARK: ApiResponseImpl
	@Override
	public boolean getResponseSuccess() {
		return this.success;
	}

	@Override
	public ApiResponseData getResponsePrimaryData() {
		return this.data;
	}

	@Override
	public Map<String, ApiResponseData> getResponseIncludedData() {
		return this.included;
	}

	@Override
	public List<ApiResponseError> getResponseErrors() {
		return this.errors;
	}

}
