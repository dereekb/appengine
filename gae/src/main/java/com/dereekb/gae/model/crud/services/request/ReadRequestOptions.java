package com.dereekb.gae.model.crud.services.request;

/**
 * Options for a {@link ReadRequest}.
 *
 * @author dereekb
 *
 */
public final class ReadRequestOptions extends AtomicRequestOptions {

	public ReadRequestOptions() {
		super(true);
	}

	public ReadRequestOptions(boolean atomic) {
		super(atomic);
	}

}
