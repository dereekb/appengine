package com.dereekb.gae.model.crud.services.request.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.CreateRequestOptions;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Default implementation of {@link CreateRequest}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class CreateRequestImpl<T extends UniqueModel>
        implements CreateRequest<T> {

	private Collection<T> templates;
	private CreateRequestOptions options;

	public CreateRequestImpl(T template) {
		this(template, null);
	}

	public CreateRequestImpl(T template, CreateRequestOptions options) {
		this(SingleItem.withValue(template), options);
	}

	public CreateRequestImpl(Collection<T> templates) {
		this(templates, null);
	}

	public CreateRequestImpl(Collection<T> templates, CreateRequestOptions options) {

		if (templates == null) {
			throw new IllegalArgumentException("Templates cannot be null.");
		}

		this.templates = templates;
		this.setOptions(options);
	}

	@Override
	public Collection<T> getTemplates() {
		return this.templates;
	}

	public void setTemplates(Collection<T> templates) {
		this.templates = templates;
	}

	@Override
	public CreateRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(CreateRequestOptions options) {
		if (this.options == null) {
			options = new CreateRequestOptions();
		}

		this.options = options;
	}

}
