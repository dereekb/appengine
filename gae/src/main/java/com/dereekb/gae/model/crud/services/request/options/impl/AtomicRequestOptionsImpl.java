package com.dereekb.gae.model.crud.services.request.options.impl;

import com.dereekb.gae.model.crud.services.request.options.AtomicRequestOptions;

/**
 * Basic request options.
 *
 * @author dereekb
 *
 */
public abstract class AtomicRequestOptionsImpl
        implements AtomicRequestOptions {

	/**
	 * Whether all objects must be used successfully in order to succeed or not.
	 */
	private boolean atomic;

	public AtomicRequestOptionsImpl() {
		this.atomic = true;
	}

	public AtomicRequestOptionsImpl(boolean atomic) {
		this.atomic = atomic;
	}

	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

}
