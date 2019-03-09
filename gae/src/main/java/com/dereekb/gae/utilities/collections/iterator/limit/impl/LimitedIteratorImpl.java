package com.dereekb.gae.utilities.collections.iterator.limit.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.dereekb.gae.utilities.collections.iterator.limit.LimitedIterator;

/**
 * {@link LimitedIterator} implementation that wraps another {@link Iterator}.
 *
 * @author dereekb
 *
 */
public class LimitedIteratorImpl<T>
        implements LimitedIterator<T> {

	private int index = 0;

	private final int iteratorLimit;
	private final Iterator<T> iterator;

	public LimitedIteratorImpl(Iterator<T> iterator, int iteratorLimit) throws IllegalArgumentException {
		if (iterator == null) {
			throw new IllegalArgumentException("Input iterator cannot be null.");
		}

		if ((iteratorLimit >= this.index) == false) {
			throw new IllegalArgumentException("The limit must be greater or equal to the current index.");
		}

		this.iteratorLimit = iteratorLimit;
		this.iterator = iterator;
	}

	@Override
	public int getIteratorLimit() {
		return this.iteratorLimit;
	}

	public int getIndex() {
		return this.index;
	}

	public Iterator<T> getIterator() {
		return this.iterator;
	}

	@Override
	public boolean hasReachedIteratorLimit() {
		return this.index >= this.iteratorLimit;
	}

	// MARK: Iterator
	@Override
	public boolean hasNext() {
		return this.iterator.hasNext() && (this.hasReachedIteratorLimit() == false);
	}

	@Override
	public T next() {
		if (this.hasReachedIteratorLimit()) {
			throw new NoSuchElementException("Iterator limit reached. Check ending using hasNext() in the future.");
		} else {
			this.index += 1;
			return this.iterator.next();
		}
	}

	@Override
	public void remove() {
		this.iterator.remove();
	}

	@Override
	public String toString() {
		return "LimitedIteratorImpl [index=" + this.index + ", iteratorLimit=" + this.iteratorLimit + ", iterator="
		        + this.iterator + "]";
	}

}
