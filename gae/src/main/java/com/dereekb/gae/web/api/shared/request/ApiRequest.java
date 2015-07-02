package com.dereekb.gae.web.api.shared.request;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Wrapper for API Request data.
 *
 * @author dereekb
 *
 * @param <I>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequest<I> {

	@Valid
	protected I data;

	public ApiRequest() {}

	public ApiRequest(I data) {
		this.data = data;
	}

	public I getData() {
		return this.data;
	}

	public void setData(I data) {
		this.data = data;
	}

}
