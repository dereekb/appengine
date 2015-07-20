package com.dereekb.gae.utilities.collections.iterator.limit;

import java.util.Iterator;

/**
 * {@link Iterator} with a set limit. The {@link Iterator} cannot iterate more
 * than the specified amount.
 *
 * @author dereekb
 *
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
	 * Sets the {@link LimitedIterator}'s iteration limit.
	 *
	 * @param limit
	 *            {@code int} value between 1 and the maximum for the
	 *            implementation.
	 * @throws IllegalArgumentException
	 *             if the limit is less than 1, or above the maximum allowed for
	 *             that {@link LimitedIterator}.
	 */
	public void setIteratorLimit(int limit) throws IllegalArgumentException;

}
