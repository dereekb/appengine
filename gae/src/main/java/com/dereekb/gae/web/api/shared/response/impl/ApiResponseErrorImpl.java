package com.dereekb.gae.web.api.shared.response.impl;

import com.dereekb.gae.utilities.web.error.impl.ErrorInfoImpl;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Used in {@link ApiResponse} to contain error information.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_EMPTY)
public class ApiResponseErrorImpl extends ErrorInfoImpl
        implements ApiResponseError {

	private Object data;

	public ApiResponseErrorImpl(String code) throws IllegalArgumentException {
		super(code);
	}

	public ApiResponseErrorImpl(String code, String title) throws IllegalArgumentException {
		super(code, title);
	}

	public ApiResponseErrorImpl(String code, String title, String detail) throws IllegalArgumentException {
		super(code, title, detail);
	}

	public ApiResponseErrorImpl(String code, Object data) throws IllegalArgumentException {
		this(code, null, null, data);
	}

	public ApiResponseErrorImpl(String code, String title, Object data) throws IllegalArgumentException {
		this(code, title, null, data);
	}

	public ApiResponseErrorImpl(String code, String title, String detail, Object data) throws IllegalArgumentException {
		super(code, title, detail);
		this.setData(data);
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	// MARK: ApiResponseError
	@JsonIgnore
	@Override
	public Object getErrorData() {
		return this.data;
	}

}
