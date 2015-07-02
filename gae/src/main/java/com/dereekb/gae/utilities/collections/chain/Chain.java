package com.dereekb.gae.utilities.collections.chain;

import java.util.Iterator;

/**
 * Singlely linked list that can iterate through itself.
 * 
 * Due to type safety, sub classes of this need to implement the getIterator() function.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public abstract class Chain<T extends Chain<T>>
        implements Iterable<T> {

	protected T next;

	public boolean hasNext() {
		return (this.next != null);
	}

	public T next() {
		return this.next;
	}

	public void setNext(T next) {
		this.next = next;
	}

	public T chain(T ordering) throws NullPointerException, IllegalArgumentException {

		if (this.next != null) {
			this.next.chain(ordering);
		} else {
			this.next = ordering;
		}

		return this.self();
	}

	/**
	 * Way for the {@link Chain} class to retrieve itself safely as the implementing type.
	 * 
	 * @return this
	 */
	protected abstract T self();

	@Override
	public ChainIterator iterator() {
		ChainIterator iterator = new ChainIterator(this.self());
		return iterator;
	}

	private class ChainIterator
	        implements Iterator<T>, Iterable<T> {

		private final T start;
		private T current;
		private T previous;
		private boolean didRemoveNow = true;

		public ChainIterator(T start) {
			this.start = start;
			current = start;
		}

		@Override
		public boolean hasNext() {
			return (previous == null || current.hasNext());
		}

		@Override
		public T next() {
			T current = this.current;

			if (previous != null) {
				T next = current.next();
				this.previous = current;
				this.current = next;
				current = next;
			} else {
				this.previous = current;
			}

			didRemoveNow = false;
			return current;
		}

		@Override
		public void remove() {
			if (this.previous != null && (didRemoveNow == false)) {
				T next = current.next();
				this.previous.setNext(next);
				didRemoveNow = true;
			}
		}

		/**
		 * Creates a copy of this {@link ChainIterator}, starting from the start of the chain.
		 * 
		 * @return
		 */
		public ChainIterator copy() {
			ChainIterator iterator = new ChainIterator(this.start);
			return iterator;
		}

		@Override
		public Iterator<T> iterator() {
			return this.copy();
		}

	}

	@Override
	public String toString() {
		return "Chain [next=" + next + "]";
	}

}
