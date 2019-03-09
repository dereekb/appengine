package com.dereekb.gae.utilities.collections.iterator.index;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.iterator.index.exception.InvalidIteratorIndexException;

/**
 * {@link Iterable} value that can set the starting index, encoded in a
 * {@link ModelKey} value.
 *
 * @author dereekb
 *
 * @param <T>
 *            Type to iterate.
 * @see IndexedIterator
 */
public interface IndexedIterable<T>
        extends Iterable<T> {

	/**
	 * Returns the index this {@link Iterable} begins at.
	 *
	 * @return {@link ModelKey} for the index to start at, or {@code null} if
	 *         one has not yet been set.
	 */
	public ModelKey getStartIndex();

	/**
	 * Sets the new start index value.
	 *
	 * @param index
	 *            {@link ModelKey} index. {@code null} to clear the current
	 *            value.
	 * @throws InvalidIteratorIndexException
	 *             if {@code index} is invalid.
	 */
	public void setStartIndex(ModelKey index) throws InvalidIteratorIndexException;

	/**
	 * Generates a new {@link IndexedIterator} for this value.
	 *
	 * @return {@link IndexedIterator}. The iterator's
	 *         {@link IndexedIterator#getStartIndex()} should match the value
	 *         from {@link #getStartIndex()}, if one has been set.
	 */
	@Override
	public IndexedIterator<T> iterator();

}
