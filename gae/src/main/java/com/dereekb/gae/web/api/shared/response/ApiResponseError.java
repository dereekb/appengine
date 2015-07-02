package com.dereekb.gae.web.api.shared.response;

/**
 * Used in {@link ApiResponse} to contain error information.
 *
 * @author dereekb
 */
public final class ApiResponseError {

	private String code;

	private String title;
	private String detail;

	private Object data;

	public ApiResponseError() {}

	public ApiResponseError(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public ApiResponseError(String code, String title, String detail) {
		this.code = code;
		this.title = title;
		this.detail = detail;
	}

	public ApiResponseError(String code, String title, String detail, Object data) {
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

}
