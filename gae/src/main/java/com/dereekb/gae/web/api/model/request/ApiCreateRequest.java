package com.dereekb.gae.web.api.model.request;

import java.util.List;

import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.CreateRequestOptionsImpl;
import com.dereekb.gae.web.api.shared.request.ApiRequest;

/**
 * Model API Request for creating elements.
 *
 * Requests have a List of one or more templates, and a set of options.
 *
 * @author dereekb
 *
 * @param <I>
 *            model dto type
 */
public final class ApiCreateRequest<I> extends ApiRequest<List<I>> {

	private boolean atomic = true;

	public ApiCreateRequest() {}

	public ApiCreateRequest(List<I> templates) {
		super(templates);
	}

	public List<I> getTemplates() {
		return this.getData();
	}

	public void setTemplates(List<I> templates) {
		this.setData(templates);
	}

	public CreateRequestOptions getOptions() {
		return new CreateRequestOptionsImpl(this.atomic);
	}

}
