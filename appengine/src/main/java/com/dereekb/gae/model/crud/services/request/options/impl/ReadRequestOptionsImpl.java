package com.dereekb.gae.model.crud.services.request.options.impl;

import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;

public class ReadRequestOptionsImpl extends AtomicRequestOptionsImpl
        implements ReadRequestOptions {

	public ReadRequestOptionsImpl() {
		super(true);
	}

	public ReadRequestOptionsImpl(boolean atomic) {
		super(atomic);
	}

}
