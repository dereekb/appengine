package com.dereekb.gae.web.api.model.request;

import java.util.List;

import com.dereekb.gae.web.api.shared.request.ApiRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Abstract edit request type
 * 
 * @author dereekb
 *
 * @param <I>
 *            model dto type
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ApiEditRequest<I> extends ApiRequest<I> {

	protected boolean atomic = true;

	public ApiEditRequest() {
		super();
	}

	public ApiEditRequest(List<I> data) {
		super(data);
	}

	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

}
