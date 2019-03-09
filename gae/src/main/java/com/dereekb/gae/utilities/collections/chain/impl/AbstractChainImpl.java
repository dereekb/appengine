package com.dereekb.gae.utilities.collections.chain.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.dereekb.gae.utilities.collections.chain.Chain;
import com.dereekb.gae.utilities.collections.chain.ChainIterable;

/**
 * {@link Chain} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            value type
 * @param <C>
 *            chain type
 */
public abstract class AbstractChainImpl<T, C extends AbstractChainImpl<T, C>>
        implements Chain<T> {

	protected T value;
	protected C next;

	public AbstractChainImpl(T value) {
		this.value = value;
	}

	protected AbstractChainImpl(T value, C next) {
		this.value = value;
		this.next = next;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public C next() {
		return this.next;
	}

	@Override
	public boolean hasNext() {
		return (this.next != null);
	}

	@Override
	public C insert(T element) throws NullPointerException {
		C newest;

		if (this.next != null) {
			this.next = this.make(element, this.next);
			newest = this.next;
		} else {
			newest = this.chain(element);
		}

		return newest;
	}

	@Override
	public C chain(T element) throws NullPointerException, IllegalArgumentException {
		C newest;

		if (this.next != null) {
			newest = this.next.chain(element);
		} else {
			this.next = this.make(element, null);
			newest = this.next;
		}

		return newest;
	}

	/**
	 * Safely creates a new element of this type with the input parameters.
	 */
	protected abstract C make(T element,
	                          C next);

	@Override
	public T removeNext() {
		T value = null;

		if (this.next != null) {
			value = this.next.getValue();
			C after = this.next.next();
			this.next = after;
		} else {
			this.next = null;
		}

		return value;
	}

	@Override
	public void breakNext() {
		this.next = null;
	}

	@Override
	public ChainIterable<T> iterator() {
		return new ValueIterator(this.self());
	}

	@Override
	public ChainIterable<C> chainIterable() {
		return new ChainIterator(this.self());
	}

	/**
	 * Safe casting to self.
	 *
	 * @return self.
	 */
	protected abstract C self();

	@Override
	public String toString() {
		return this.separatedToString(", ");
	}

	public String toString(String separator) throws NullPointerException {
		if (separator == null) {
			throw new NullPointerException("Separator cannot be null.");
		}

		return this.separatedToString(separator);
	}

	protected String separatedToString(String separator) {
		return (this.value + ((this.next != null) ? separator + this.next.toString(separator) : ""));
	}

	private class ValueIterator
	        implements ChainIterable<T> {

		private final ChainIterator iterator;

		public ValueIterator(C start) {
			this(new ChainIterator(start));
		}

		public ValueIterator(ChainIterator iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		@Override
		public T next() {
			C next = this.iterator.next();

			if (next == null) {
				throw new NoSuchElementException();
			}

			return next.getValue();
		}

		@Override
		public void remove() {
			this.iterator.remove();
		}

		@Override
		public Iterator<T> iterator() {
			return new ValueIterator(this.iterator);
		}

	}

	private class ChainIterator
	        implements ChainIterable<C> {

		// private final C start;
		private C current;
		private C previous;
		private boolean didRemoveNow = true;

		public ChainIterator(C start) {
			// this.start = start;
			this.current = start;
		}

		@Override
		public boolean hasNext() {
			return (this.previous == null || this.current.hasNext());
		}

		@Override
		public C next() {
			C current = this.current;

			if (this.previous != null) {
				C next = current.next();
				this.previous = current;
				this.current = next;
				current = next;
			} else {
				this.previous = current;
			}

			if (current == null) {
				throw new NoSuchElementException();
			}

			this.didRemoveNow = false;
			return current;
		}

		@Override
		public void remove() {
			if (this.previous != null && (this.didRemoveNow == false)) {
				this.previous.next = this.current.next();
				this.didRemoveNow = true;
			}
		}

		@Override
		public ChainIterator iterator() {
			return new ChainIterator(this.current);
		}

	}

}
