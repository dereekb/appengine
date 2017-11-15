package com.dereekb.gae.model.crud.services.request.options;

/**
 * Interface concerning atomic options.
 *
 * @author dereekb
 *
 */
public interface AtomicRequestOptions {

	/**
	 * If the request is atomic.
	 * 
	 * @return {@code true} if atomic.
	 */
	public boolean isAtomic();

	/**
	 * Set atomic.
	 * 
	 * @param atomic
	 */
	public void setAtomic(boolean atomic);

}
