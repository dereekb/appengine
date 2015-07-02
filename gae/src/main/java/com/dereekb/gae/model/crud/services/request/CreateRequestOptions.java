package com.dereekb.gae.model.crud.services.request;

/**
 * @see {@link CreateRequest}
 * @author dereekb
 */
public final class CreateRequestOptions extends AtomicRequestOptions {

	public CreateRequestOptions() {
		super(true);
	}

	public CreateRequestOptions(boolean atomic) {
		super(atomic);
	}

}
