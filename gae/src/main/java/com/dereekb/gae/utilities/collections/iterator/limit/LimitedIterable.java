package com.dereekb.gae.utilities.collections.iterator.limit;

/**
 * {@link Iterable} implementation that wraps another iterable source.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LimitedIterable<T>
        extends Iterable<T> {

	/**
	 * Returns the {@link LimitedIterable}'s iteration limit.
	 *
	 * @return {@code int} value greater than 0.
	 */
	public int getIteratorLimit();

	/**
	 * Returns a configured {@link LimitedIterator} that returns elements.
	 *
	 * @return {@link LimitedIterator}. Never {@code null}.
	 */
	@Override
	public LimitedIterator<T> iterator();

}
