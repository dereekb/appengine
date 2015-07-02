package com.thevisitcompany.gae.deprecated.web.api;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Wrapper for API Request data. Uploaded data is validated if a validator is available.
 *
 * @author dereekb
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequest<T> {

	private String type;

	@Valid
	private T data;

	public ApiRequest() {}

	public ApiRequest(T data) {
		this.data = data;
	}

	public ApiRequest(String type) {
		this.type = type;
	}

	public ApiRequest(T data, String type) {
		this.data = data;
		this.type = type;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
