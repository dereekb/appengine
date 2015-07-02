package com.dereekb.gae.model.crud.services.request;

/**
 * @see {@link UpdateRequest}
 * @author dereekb
 */
public class UpdateRequestOptions extends AtomicRequestOptions {

	public UpdateRequestOptions() {
		super(true);
	}

	public UpdateRequestOptions(boolean atomic) {
		super(atomic);
	}

}
