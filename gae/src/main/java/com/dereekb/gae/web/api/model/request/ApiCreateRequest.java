package com.dereekb.gae.web.api.model.request;

import java.util.List;

import javax.validation.Valid;

import com.dereekb.gae.model.crud.services.request.CreateRequestOptions;
import com.dereekb.gae.web.api.shared.request.ApiRequest;

/**
 * Model API Request for creating elements.
 *
 * Requests have a List of one or more templates, and a set of options.
 *
 * @author dereekb
 *
 * @param <I>
 */
public final class ApiCreateRequest<I> extends ApiRequest<List<I>> {

	@Valid
	private CreateRequestOptions options;

	public ApiCreateRequest() {}

	public ApiCreateRequest(List<I> templates) {
		super(templates);
	}

	public List<I> getTemplates() {
		return this.data;
	}

	public void setTemplates(List<I> templates) {
		this.data = templates;
	}

	public CreateRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(CreateRequestOptions options) {
		this.options = options;
	}

}
