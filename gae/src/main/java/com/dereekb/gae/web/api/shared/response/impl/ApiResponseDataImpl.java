package com.dereekb.gae.web.api.shared.response.impl;

import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Wraps response data with a type.
 * <p>
 * Is used for only serialization, and is not useful for deserialization.
 * <p>
 * Only the contained data is serialized.
 *
 * @author dereekb
 *
 * @see ApiResponse
 */
@JsonInclude(Include.NON_DEFAULT)
public final class ApiResponseDataImpl
        implements ApiResponseData {

	private transient boolean wrapped = false;

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

	public static ApiResponseData makeTypeDataWrap(ApiResponseData data) {
		return new ApiResponseTypeDataWrapper(data);
	}

	@JsonIgnore
	public String getType() {
		return this.type;
	}

	public void setType(String type) throws IllegalArgumentException {
		if (type == null || type.isEmpty()) {
			throw new IllegalArgumentException("Response Data Type cannot be null or empty.");
		}

		this.type = type.toLowerCase();
	}

	/**
	 * The data is returned as the JSON value.
	 *
	 * @return {@link Object}. Never {@code null}.
	 */
	@JsonValue
	public Object getData() {
		return this.data;
	}

	public void setData(Object data) throws IllegalArgumentException {
		if (data == null) {
			throw new IllegalArgumentException("Response Data cannot be null.");
		}

		this.data = data;
	}

	public void includeTypeInData() {
		if (!this.wrapped) {
			this.data = new ApiResponseTypeDataWrapper(this);
			this.wrapped = true;
		}
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

	// MARK: Internal
	public static class ApiResponseTypeDataWrapper
	        implements ApiResponseData {

		private String type;
		private Object data;

		public ApiResponseTypeDataWrapper(ApiResponseData data) {
			this.type = data.getResponseDataType();
			this.data = data.getResponseData();
		}

		public String getType() {
			return this.type;
		}

		public Object getData() {
			return this.data;
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

	}

	@Override
	public String toString() {
		return "ApiResponseDataImpl [type=" + this.type + ", data=" + this.data + "]";
	}

}
