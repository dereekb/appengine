package com.dereekb.gae.model.crud.services.request.options.impl;

import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;

/**
 * {@link CreateRequestOptions} implementation.
 * 
 * @author dereekb
 *
 */
public class CreateRequestOptionsImpl extends AtomicRequestOptionsImpl
        implements CreateRequestOptions {

	public CreateRequestOptionsImpl() {
		super();
	}

	public CreateRequestOptionsImpl(CreateRequestOptions options) {
		super(options.isAtomic());
	}

	public CreateRequestOptionsImpl(boolean atomic) {
		super(atomic);
	}

}
