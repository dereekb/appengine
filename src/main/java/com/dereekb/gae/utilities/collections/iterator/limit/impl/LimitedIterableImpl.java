package com.dereekb.gae.utilities.collections.iterator.limit.impl;

import com.dereekb.gae.utilities.collections.iterator.limit.LimitedIterable;
import com.dereekb.gae.utilities.collections.iterator.limit.LimitedIterator;

/**
 * {@link LimitedIterable} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LimitedIterableImpl<T>
        implements LimitedIterable<T> {

	private int iteratorLimit;
	private Iterable<T> iterable;

	public LimitedIterableImpl(Iterable<T> iterable, int iteratorLimit) throws IllegalArgumentException {
		this.setIterable(iterable);
		this.setIteratorLimit(iteratorLimit);
	}

	public Iterable<T> getIterable() {
		return this.iterable;
	}

	private void setIterable(Iterable<T> iterable) throws IllegalArgumentException {
		if (iterable == null) {
			throw new IllegalArgumentException("Iterable cannot be null.");
		}

		this.iterable = iterable;
	}

	@Override
	public int getIteratorLimit() {
		return this.iteratorLimit;
	}

	public void setIteratorLimit(int iteratorLimit) throws IllegalArgumentException {
		if (iteratorLimit < 0) {
			throw new IllegalArgumentException("The limit must be greater than zero.");
		}

		this.iteratorLimit = iteratorLimit;
	}

	@Override
	public LimitedIterator<T> iterator() {
		return new LimitedIteratorImpl<T>(this.iterable.iterator(), this.iteratorLimit);
	}

	@Override
	public String toString() {
		return "LimitedIterableImpl [iteratorLimit=" + this.iteratorLimit + ", iterable=" + this.iterable + "]";
	}

}
