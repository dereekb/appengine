package com.dereekb.gae.model.crud.services.request.options.impl;

import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;


public class CreateRequestOptionsImpl extends AtomicRequestOptionsImpl
        implements CreateRequestOptions {

	public CreateRequestOptionsImpl() {
		super();
	}

	public CreateRequestOptionsImpl(boolean atomic) {
		super(atomic);
	}

}
