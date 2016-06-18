package com.dereekb.gae.web.api.model.request;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.CreateRequestOptionsImpl;
import com.dereekb.gae.web.api.shared.request.ApiRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ApiCreateRequest<I> extends ApiRequest<I> {

	private boolean atomic = true;

	public ApiCreateRequest() {}

	public ApiCreateRequest(List<I> templates) {
		super(templates);
	}

	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	@JsonIgnore
	public List<I> getTemplates() {
		return this.getData();
	}

	public void setTemplate(I template) throws IllegalArgumentException {
		List<I> templates = new ArrayList<I>();

		if (template != null) {
			templates.add(template);
		} else {
			throw new IllegalArgumentException("Template cannot be null.");
		}

		this.setData(templates);
	}

	@JsonIgnore
	public void setTemplates(List<I> templates) {
		this.setData(templates);
	}

	@JsonIgnore
	public CreateRequestOptions getOptions() {
		return new CreateRequestOptionsImpl(this.atomic);
	}

	@Override
	public String toString() {
		return "ApiCreateRequest [atomic=" + this.atomic + ", data=" + this.data + "]";
	}

}
