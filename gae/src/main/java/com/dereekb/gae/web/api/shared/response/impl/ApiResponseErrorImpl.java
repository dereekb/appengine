package com.dereekb.gae.web.api.shared.response.impl;

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
public class ApiResponseErrorImpl
        implements ApiResponseError {

	private String code;

	private String title;
	private String detail;

	private Object data;

	public ApiResponseErrorImpl() {}

	public ApiResponseErrorImpl(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public ApiResponseErrorImpl(String code, String title, String detail) {
		this.code = code;
		this.title = title;
		this.detail = detail;
	}

	public ApiResponseErrorImpl(String code, String title, String detail, Object data) {
		this.code = code;
		this.title = title;
		this.detail = detail;
		this.data = data;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
	public String getErrorCode() {
		return this.code;
	}

	@JsonIgnore
	@Override
	public String getErrorTitle() {
		return this.title;
	}

	@JsonIgnore
	@Override
	public String getErrorDetail() {
		return this.detail;
	}

	@JsonIgnore
	@Override
	public Object getErrorData() {
		return this.data;
	}

	@Override
	public String toString() {
		return "ApiResponseErrorImpl [code=" + this.code + ", title=" + this.title + ", detail=" + this.detail
		        + ", data=" + this.data + "]";
	}

}
