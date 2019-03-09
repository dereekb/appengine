package com.dereekb.gae.web.api.model.extension.search.impl;

import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link ApiResponseDataImpl} extension that includes query-related results.
 * 
 * @author dereekb
 * 
 */
@JsonInclude(Include.NON_DEFAULT)
public class ApiSearchResponseData extends ApiResponseDataImpl {

	private String cursor = null;

	public ApiSearchResponseData() {
		super();
	}

	public ApiSearchResponseData(String type) throws IllegalArgumentException {
		super(type);
	}

	public ApiSearchResponseData(String type, Object data) throws IllegalArgumentException {
		super(type, data);
	}

	public String getCursor() {
		return this.cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	@Override
	public String toString() {
		return "ApiSearchResponseData [cursor=" + this.cursor + "]";
	}

}
