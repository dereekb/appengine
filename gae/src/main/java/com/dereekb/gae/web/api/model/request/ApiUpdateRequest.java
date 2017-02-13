package com.dereekb.gae.web.api.model.request;

import java.util.List;

import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.UpdateRequestOptionsImpl;
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
public class ApiUpdateRequest<I> extends ApiEditRequest<I>
        implements UpdateRequestOptions {

	public ApiUpdateRequest() {}

	public ApiUpdateRequest(List<I> elements) {
		super(elements);
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
	public UpdateRequestOptions getOptions() {
		return new UpdateRequestOptionsImpl(this);
	}

	@JsonIgnore
	public void setOptions(UpdateRequestOptions options) {
		this.atomic = options.isAtomic();
	}

	@Override
	public String toString() {
		return "ApiUpdateRequest [atomic=" + this.atomic + ", data=" + this.data + "]";
	}

}
