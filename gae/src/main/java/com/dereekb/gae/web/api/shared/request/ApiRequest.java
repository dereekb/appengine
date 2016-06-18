package com.dereekb.gae.web.api.shared.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Wrapper for API Request data.
 *
 * @author dereekb
 *
 * @param <I>
 *            data type
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRequest<I> {

	@Valid
	@NotNull
	protected List<I> data;

	public ApiRequest() {}

	public ApiRequest(List<I> data) {
		this.data = data;
	}

	public List<I> getData() {
		return this.data;
	}

	public void setData(List<I> data) throws IllegalArgumentException {
		if (data == null) {
			throw new IllegalArgumentException("Data cannot be null.");
		}

		this.data = data;
	}

	@Override
	public String toString() {
		return "ApiRequest [data=" + this.data + "]";
	}

}
