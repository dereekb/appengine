package com.dereekb.gae.model.crud.services.request.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.CreateRequestOptionsImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.model.crud.request.ApiCreateRequest;

/**
 * Default implementation of {@link CreateRequest}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see ApiCreateRequest
 */
public final class CreateRequestImpl<T extends UniqueModel>
        implements CreateRequest<T> {

	private List<T> templates;

	private CreateRequestOptions options;

	public CreateRequestImpl(T template) {
		this(template, null);
	}

	public CreateRequestImpl(T template, CreateRequestOptions options) {
		List<T> list = new ArrayList<T>();
		list.add(template);

		this.setTemplates(list);
		this.setOptions(options);
	}

	public CreateRequestImpl(List<T> templates) {
		this(templates, null);
	}

	public CreateRequestImpl(List<T> templates, CreateRequestOptions options) {
		if (templates == null) {
			throw new IllegalArgumentException("Templates cannot be null.");
		}

		this.setTemplates(templates);
		this.setOptions(options);
	}

	@Override
	public List<T> getTemplates() {
		return this.templates;
	}

	public void setTemplates(List<T> templates) {
		this.templates = templates;
	}

	@Override
	public CreateRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(CreateRequestOptions options) {
		if (this.options == null) {
			options = new CreateRequestOptionsImpl();
		}

		this.options = options;
	}

}
