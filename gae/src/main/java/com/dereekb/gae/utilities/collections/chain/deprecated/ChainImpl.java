package com.dereekb.gae.utilities.collections.chain.impl;

import java.util.Iterator;

import com.dereekb.gae.utilities.collections.chain.Chain;

/**
 * Single-ly linked list.
 * <p>
 * Due to type safety, sub classes of this need to implement the getIterator()
 * function.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ChainImpl<T>
        implements Chain<T> {

	protected T value;
	protected ChainImpl<T> next;

	public ChainImpl(T value) {
		this.value = value;
	}

	private ChainImpl(T value, ChainImpl<T> next) {
		this.value = value;
		this.next = next;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public ChainImpl<T> next() {
		return this.next;
	}

	@Override
	public boolean hasNext() {
		return (this.next != null);
	}

	@Override
	public ChainImpl<T> insert(T element) throws NullPointerException {
		ChainImpl<T> newest;

		if (this.next != null) {
			this.next = new ChainImpl<T>(element, this.next);
			newest = this.next;
		} else {
			newest = this.chain(element);
		}

		return newest;
    }

	@Override
	public ChainImpl<T> chain(T element) throws NullPointerException, IllegalArgumentException {
		ChainImpl<T> newest;

		if (this.next != null) {
			newest = this.next.chain(element);
		} else {
			this.next = new ChainImpl<T>(element);
			newest = this.next;
		}

		return newest;
	}

	@Override
	public T removeNext() {
		T value = null;

		if (this.next != null) {
			value = this.next.getValue();
			ChainImpl<T> after = this.next.next();
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
	public ChainIterator<T> iterator() {
		return new ChainIterator<T>(this);
	}

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

	private static class ChainIterator<T>
	        implements Iterator<T>, Iterable<T> {

		// private final ChainImpl<T> start;
		private ChainImpl<T> current;
		private ChainImpl<T> previous;
		private boolean didRemoveNow = true;

		public ChainIterator(ChainImpl<T> start) {
			// this.start = start;
			this.current = start;
		}

		@Override
		public boolean hasNext() {
			return (this.previous == null || this.current.hasNext());
		}

		@Override
		public T next() {
			ChainImpl<T> current = this.current;

			if (this.previous != null) {
				ChainImpl<T> next = current.next();
				this.previous = current;
				this.current = next;
				current = next;
			} else {
				this.previous = current;
			}

			this.didRemoveNow = false;
			return current.getValue();
		}

		@Override
		public void remove() {
			if (this.previous != null && (this.didRemoveNow == false)) {
				this.previous.next = this.current.next();
				this.didRemoveNow = true;
			}
		}

		@Override
		public Iterator<T> iterator() {
			return new ChainIterator<T>(this.current);
		}

	}

	@Override
	public Iterator<? extends Chain<T>> chainIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
