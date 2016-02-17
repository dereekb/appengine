package com.dereekb.gae.web.api.model.request;

import java.util.List;

import javax.validation.Valid;

import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.web.api.shared.request.ApiRequest;

/**
 * Request for deleting one or more elements via their identifiers.
 *
 * @author dereekb
 */
public final class ApiDeleteRequest extends ApiRequest<List<String>> {

	@Valid
	private DeleteRequestOptions options;

	public ApiDeleteRequest() {
		super();
	}

	public ApiDeleteRequest(List<String> ids) {
		super(ids);
	}

	public DeleteRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(DeleteRequestOptions options) {
		this.options = options;
	}

}
