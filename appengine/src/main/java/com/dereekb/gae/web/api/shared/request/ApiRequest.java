package com.dereekb.gae.web.api.shared.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@NotEmpty
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

	@JsonIgnore
	public void setDataElement(I data) throws IllegalArgumentException {
		List<I> templates = new ArrayList<I>();

		if (data != null) {
			templates.add(data);
		} else {
			throw new IllegalArgumentException("Template cannot be null.");
		}

		this.setData(templates);
	}

	@Override
	public String toString() {
		return "ApiRequest [data=" + this.data + "]";
	}

}
