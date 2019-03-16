package com.dereekb.gae.utilities.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class SingleItem<T>
        implements Collection<T>, Iterable<T> {

	private class SingleIterator
	        implements Iterator<T> {

		private boolean hasSentNext = false;

		@Override
		public boolean hasNext() {
			return (this.hasSentNext == false);
		}

		@Override
		public T next() {
			this.hasSentNext = true;
			return SingleItem.this.value;
		}

		@Override
		public void remove() {}

	}

	public final T value;

	public SingleItem(T value) {
		if (value == null) {
			throw new IllegalArgumentException("SingleItem value cannot be null.");
		}

		this.value = value;
	}

	/**
	 * Creates a new {@link SingleItem} wrapping the input value.
	 *
	 * @param value
	 *            Value to wrap.
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static <T> SingleItem<T> withValue(T value) throws IllegalArgumentException {
		return new SingleItem<T>(value);
	}

	@Override
	public Iterator<T> iterator() {
		return new SingleIterator();
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return this.value.equals(o);
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[] { this.value };
		return array;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> E[] toArray(E[] a) {
		Object[] array = this.toArray();
		if (a.length < 1) {
			return (E[]) Arrays.copyOf(array, 1, a.getClass());
		}
		System.arraycopy(array, 0, a, 0, 1);
		if (a.length > 1) {
			a[1] = null;
		}
		return a;
	}

	@Override
	public boolean add(T e) {
		throw new RuntimeException("SingleItem collection is immutable.");
	}

	@Override
	public boolean remove(Object o) {
		throw new RuntimeException("SingleItem collection is immutable.");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean containsAll = false;

		if (c.size() == 1) {
			containsAll = c.contains(this.value);
		}

		return containsAll;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new RuntimeException("SingleItem collection is immutable.");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new RuntimeException("SingleItem collection is immutable.");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new RuntimeException("SingleItem collection is immutable.");
	}

	@Override
	public void clear() {
		throw new RuntimeException("SingleItem collection is immutable.");
	}

	@Override
	public String toString() {
		return "{" + this.value + "}";
	}

}
