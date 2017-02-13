package com.dereekb.gae.web.api.model.request;

import java.util.List;

import javax.validation.Valid;

import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.UpdateRequestOptionsImpl;
import com.dereekb.gae.web.api.shared.request.ApiRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Model API Request for updating elements.
 *
 * @author dereekb
 *
 * @param <I>
 *            The input elements. Null values on these elements will be ignored.
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiUpdateRequest<I> extends ApiRequest<I> {

	@Valid
	private UpdateRequestOptionsImpl options;

	public ApiUpdateRequest() {}

	public ApiUpdateRequest(List<I> elements) {
		super(elements);
	}

	public UpdateRequestOptionsImpl getOptions() {
		return this.options;
	}

	public void setOptions(UpdateRequestOptionsImpl options) {
		this.options = options;
	}

	@JsonIgnore
	public void setRequestOptions(UpdateRequestOptions options) {
		this.options = new UpdateRequestOptionsImpl(options);
	}

}
