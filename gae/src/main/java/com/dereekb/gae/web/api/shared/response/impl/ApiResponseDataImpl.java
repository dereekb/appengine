package com.dereekb.gae.web.api.shared.response.impl;

import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Wraps response data with a type.
 *
 * @author dereekb
 * 
 * @see ApiResponse
 */
@JsonInclude(Include.NON_DEFAULT)
public class ApiResponseDataImpl
        implements ApiResponseData {

	private String type;

	private Object data;

	public ApiResponseDataImpl() {}

	public ApiResponseDataImpl(String type) throws IllegalArgumentException {
		this.setType(type);
	}

	public ApiResponseDataImpl(String type, Object data) throws IllegalArgumentException {
		this.setType(type);
		this.setData(data);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) throws IllegalArgumentException {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("Response Data Type cannot be null or empty.");
		}

		this.type = type.toLowerCase();
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) throws IllegalArgumentException {
		if (data == null) {
			throw new IllegalArgumentException("Response Data cannot be null.");
		}

		this.data = data;
	}

	// MARK: ApiResponseData
	@JsonIgnore
	@Override
	public String getResponseDataType() {
		return this.type;
	}

	@JsonIgnore
	@Override
	public Object getResponseData() {
		return this.data;
	}

	@Override
	public String toString() {
		return "ApiResponseDataImpl [type=" + this.type + ", data=" + this.data + "]";
	}

}
