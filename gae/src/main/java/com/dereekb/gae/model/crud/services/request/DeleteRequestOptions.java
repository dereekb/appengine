package com.dereekb.gae.model.crud.services.request;

/**
 * Additional options for {@link DeleteRequest}.
 *
 * @author dereekb
 *
 */
public final class DeleteRequestOptions extends AtomicRequestOptions {

	public DeleteRequestOptions() {
		super();
	}

	public DeleteRequestOptions(boolean atomic) {
		super(atomic);
	}

}
