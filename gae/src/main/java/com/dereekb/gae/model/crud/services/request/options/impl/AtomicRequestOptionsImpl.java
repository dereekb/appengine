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
	protected boolean atomic;

	public AtomicRequestOptionsImpl() {
		this(true);
	}

	public AtomicRequestOptionsImpl(boolean atomic) {
		this.atomic = atomic;
	}

	@Override
    public boolean isAtomic() {
		return this.atomic;
	}

	@Override
    public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

	@Override
	public String toString() {
		return "AtomicRequestOptionsImpl [atomic=" + this.atomic + "]";
	}

}
