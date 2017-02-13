package com.dereekb.gae.web.api.model.request;

import java.util.List;

import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.CreateRequestOptionsImpl;
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
public final class ApiCreateRequest<I> extends ApiEditRequest<I>
        implements CreateRequestOptions {

	public ApiCreateRequest() {}

	public ApiCreateRequest(List<I> templates) {
		super(templates);
	}

	@JsonIgnore
	public List<I> getTemplates() {
		return this.getData();
	}

	@JsonIgnore
	public void setTemplates(List<I> templates) {
		this.setData(templates);
	}

	@JsonIgnore
	public CreateRequestOptions getOptions() {
		return new CreateRequestOptionsImpl(this);
	}

	@JsonIgnore
	public void setOptions(CreateRequestOptions options) {
		this.atomic = options.isAtomic();
	}

	@Override
	public String toString() {
		return "ApiCreateRequest [atomic=" + this.atomic + ", data=" + this.data + "]";
	}

}
