package com.dereekb.gae.model.crud.services.request;


public class AtomicRequestOptions {

	/**
	 * Whether all objects must be used successfully in order to succeed or not.
	 */
	private boolean atomic;

	public AtomicRequestOptions() {
		this.atomic = true;
	}

	public AtomicRequestOptions(boolean atomic) {
		this.atomic = atomic;
	}

	public boolean isAtomic() {
		return this.atomic;
	}

	public void setAtomic(boolean atomic) {
		this.atomic = atomic;
	}

}
