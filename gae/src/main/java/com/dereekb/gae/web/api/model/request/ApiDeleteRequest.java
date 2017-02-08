package com.dereekb.gae.web.api.model.request;

import java.util.List;

import javax.validation.Valid;

import com.dereekb.gae.model.crud.services.request.options.impl.DeleteRequestOptionsImpl;
import com.dereekb.gae.web.api.shared.request.ApiRequest;

/**
 * Request for deleting one or more elements via their identifiers.
 *
 * @author dereekb
 */
public final class ApiDeleteRequest extends ApiRequest<String> {

	@Valid
	private DeleteRequestOptionsImpl options;

	private boolean returnModels = false;

	public ApiDeleteRequest() {
		super();
	}

	public ApiDeleteRequest(List<String> ids) {
		super(ids);
	}

	public DeleteRequestOptionsImpl getOptions() {
		return this.options;
	}

	public void setOptions(DeleteRequestOptionsImpl options) {
		this.options = options;
	}

	public boolean getReturnModels() {
		return this.returnModels;
	}

	public void setReturnModels(boolean returnModels) {
		this.returnModels = returnModels;
	}

	@Override
	public String toString() {
		return "ApiDeleteRequest [options=" + this.options + ", data=" + this.data + "]";
	}

}
