package com.dereekb.gae.utilities.collections.iterator.limit;

import java.util.Iterator;

/**
 * {@link Iterator} with a set limit.
 * <p>
 * The {@link Iterator} cannot iterate more than the specified amount.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LimitedIterator<T>
        extends Iterator<T> {

	/**
	 * Returns the {@link LimitedIterator}'s iteration limit.
	 *
	 * @return {@code int} value greater than 0.
	 */
	public int getIteratorLimit();

	/**
	 * Checks to see if the iterator limit has been reached.
	 * <p>
	 * Can be used in conjunction with {@link #hasNext()} to see if the the
	 * limit has been reached.
	 *
	 * @return {@code true} if the iterator limit has been reached.
	 */
	public boolean hasReachedIteratorLimit();

}
