package com.dereekb.gae.utilities.misc.bit;

/**
 * Used for manipulating {@link BitContainer} instances.
 */
public class BitAccessor<T> {

	private BitContainer<T> container;

	public BitAccessor(BitContainer<T> container) {
		this.container = container;
	}

	public BitContainer<T> getContainer() {
		return this.container;
	}

	public void setContainer(BitContainer<T> container) {
		this.container = container;
	}


}
