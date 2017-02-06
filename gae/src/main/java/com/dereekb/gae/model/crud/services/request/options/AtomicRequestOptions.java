package com.dereekb.gae.model.crud.services.request.options;

/**
 * Interface concerning atomic options.
 *
 * @author dereekb
 *
 */
public interface AtomicRequestOptions {

	public boolean isAtomic();

	public void setAtomic(boolean atomic);

}
