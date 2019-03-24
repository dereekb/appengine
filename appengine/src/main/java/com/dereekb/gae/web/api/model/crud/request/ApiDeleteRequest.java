package com.dereekb.gae.web.api.model.crud.request;

import java.util.List;

import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.DeleteRequestOptionsImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Request for deleting one or more elements via their identifiers.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ApiDeleteRequest extends ApiEditRequest<String> {

	private boolean returnModels = false;

	public ApiDeleteRequest() {
		super();
	}

	public ApiDeleteRequest(List<String> ids) {
		super(ids);
	}

	public ApiDeleteRequest(List<String> ids, boolean atomic) {
		super(ids, atomic);
	}

	public boolean getReturnModels() {
		return this.returnModels;
	}

	public void setReturnModels(boolean returnModels) {
		this.returnModels = returnModels;
	}

	@JsonIgnore
	public DeleteRequestOptions getOptions() {
		return new DeleteRequestOptionsImpl();
	}

	@JsonIgnore
	public void setOptions(DeleteRequestOptions options) {
		// Do nothing...
	}

	@JsonIgnore
	public void setTargetKeys(Iterable<? extends UniqueModel> targetKeys) {
		if (targetKeys == null) {
			this.setData(null);
		} else {
			List<String> stringKeys = ModelKey.readStringKeys(targetKeys);
			this.setData(stringKeys);
		}
	}

	@Override
	public String toString() {
		return "ApiDeleteRequest [returnModels=" + this.returnModels + ", atomic=" + this.atomic + ", data=" + this.data
		        + "]";
	}

}
