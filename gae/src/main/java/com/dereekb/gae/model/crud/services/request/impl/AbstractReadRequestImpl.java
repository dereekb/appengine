package com.dereekb.gae.model.crud.services.request.impl;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Base implementation of {@link ReadRequest} that contains
 * {@link ReadRequestOptions}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public abstract class AbstractReadRequestImpl<T extends UniqueModel>
        implements ReadRequest<T> {

	protected ReadRequestOptions options;

	public AbstractReadRequestImpl(ReadRequestOptions options) {
		this.setOptions(options);
	}

	@Override
	public ReadRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(ReadRequestOptions options) {
		if (options == null) {
			options = new ReadRequestOptions();
		}

		this.options = options;
	}

}
