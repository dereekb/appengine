package com.dereekb.gae.model.crud.services.request.options.impl;

import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;

public class UpdateRequestOptionsImpl extends AtomicRequestOptionsImpl
        implements UpdateRequestOptions {

	public UpdateRequestOptionsImpl() {
		super(true);
	}

	public UpdateRequestOptionsImpl(boolean atomic) {
		super(atomic);
	}

}
