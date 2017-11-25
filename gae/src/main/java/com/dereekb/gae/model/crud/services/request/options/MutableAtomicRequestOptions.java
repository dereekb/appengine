package com.dereekb.gae.model.crud.services.request.options;

/**
 * Mutable {@link AtomicRequestOptions}.
 *
 * @author dereekb
 *
 */
public interface MutableAtomicRequestOptions
        extends AtomicRequestOptions {

	/**
	 * Set atomic.
	 *
	 * @param atomic
	 */
	public void setAtomic(boolean atomic);

}
