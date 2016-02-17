package com.dereekb.gae.utilities.collections.chain.impl;

/**
 * {@link AbstractChainImpl} that contains an arbitrary value.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
public class ValuesChain<T> extends AbstractChainImpl<T, ValuesChain<T>> {

	public ValuesChain(T value) {
		super(value);
	}

	private ValuesChain(T value, ValuesChain<T> next) {
		super(value, next);
	}

	// MARK: AbstractChainImpl
	@Override
    protected ValuesChain<T> make(T element,
                                  ValuesChain<T> next) {
		return new ValuesChain<T>(element, next);
    }

	@Override
	protected ValuesChain<T> self() {
		return this;
	}

}
