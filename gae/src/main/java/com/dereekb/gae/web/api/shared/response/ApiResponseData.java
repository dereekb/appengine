package com.dereekb.gae.web.api.shared.response;

/**
 * Wraps response data with a type.
 *
 * @author dereekb
 */
public final class ApiResponseData {

	private String type;
	private Object data;

	public ApiResponseData(String type, Object data) {
		this.type = type;
		this.data = data;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
