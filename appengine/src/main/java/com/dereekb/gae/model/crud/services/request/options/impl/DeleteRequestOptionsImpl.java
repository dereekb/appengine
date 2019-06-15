package com.dereekb.gae.model.crud.services.request.options.impl;

import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;

/**
 * {@link DeleteRequestOptions} implementation.
 *
 * @author dereekb
 *
 */
public class DeleteRequestOptionsImpl extends AtomicRequestOptionsImpl
        implements DeleteRequestOptions {

	public DeleteRequestOptionsImpl() {
		super();
	}

	public DeleteRequestOptionsImpl(boolean atomic) {
		super(atomic);
	}

}
