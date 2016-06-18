package com.dereekb.gae.web.api.model.request;

import java.util.List;

import javax.validation.Valid;

import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.web.api.shared.request.ApiRequest;

/**
 * Model API Request for updating elements.
 *
 * @author dereekb
 *
 * @param <I>
 *            The input elements. Null values on these elements will be ignored.
 */
public final class ApiUpdateRequest<I> extends ApiRequest<I> {

	@Valid
	private UpdateRequestOptions options;

	public ApiUpdateRequest() {}

	public ApiUpdateRequest(List<I> elements) {
		super(elements);
	}

	public UpdateRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(UpdateRequestOptions options) {
		this.options = options;
	}

}
