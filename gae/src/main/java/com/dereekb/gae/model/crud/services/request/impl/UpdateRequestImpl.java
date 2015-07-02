package com.dereekb.gae.model.crud.services.request.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequestOptions;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Default implementation of {@link UpdateRequest}.
 *
 * @author dereekb
 */
public final class UpdateRequestImpl<T extends UniqueModel>
        implements UpdateRequest<T> {

	private Collection<T> templates;
	private UpdateRequestOptions options;

	public UpdateRequestImpl(T template) {
		this(template, null);
	}

	public UpdateRequestImpl(T template, UpdateRequestOptions options) {
		this(SingleItem.withValue(template), options);
	}

	public UpdateRequestImpl(Collection<T> templates) {
		this(templates, null);
	}

	public UpdateRequestImpl(Collection<T> templates, UpdateRequestOptions options) {

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
	public UpdateRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(UpdateRequestOptions options) {
		if (this.options == null) {
			options = new UpdateRequestOptions();
		}

		this.options = options;
	}

}
