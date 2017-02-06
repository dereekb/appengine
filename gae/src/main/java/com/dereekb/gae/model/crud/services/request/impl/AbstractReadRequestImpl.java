package com.dereekb.gae.model.crud.services.request.impl;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;

/**
 * Base implementation of {@link ReadRequest} that contains
 * {@link ReadRequestOptions}.
 *
 * @author dereekb
 */
public abstract class AbstractReadRequestImpl
        implements ReadRequest {

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
			options = new ReadRequestOptionsImpl();
		}

		this.options = options;
	}

}
