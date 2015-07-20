package com.dereekb.gae.utilities.collections.iterator.index;

import java.util.Iterator;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;

/**
 * {@link Iterator} that is keyed using a {@link ModelKey} value to provide
 * information about the current index.
 * <p>
 * {@link IndexedIterator} should <i>not</i> be used for instances that need the
 * numeric index for each iteration, but for instances where the number of
 * values being iterated over is too large to be executed at once.
 *
 * @author dereekb
 *
 * @param <T>
 *            Type to iterate.
 * @see {@link IndexedIterable}
 */
public interface IndexedIterator<T>
        extends Iterator<T> {

	/**
	 * Returns the index this iterator began at.
	 *
	 * @return {@link ModelKey} containing the start index. Never {@code null}.
	 * @throws UnavailableIteratorIndexException
	 *             If the index is unavailable.
	 */
	public ModelKey getStartIndex() throws UnavailableIteratorIndexException;

	/**
	 * Returns the index this iterator currently at.
	 *
	 * @return {@link ModelKey} containing the current index. Never {@code null}
	 * @throws UnavailableIteratorIndexException
	 *             If the index is unavailable.
	 */
	public ModelKey getCurrentIndex() throws UnavailableIteratorIndexException;

	/**
	 * Returns the index this iterator ends at.
	 *
	 * @return {@link ModelKey} for the end index. Never {@code null}.
	 * @throws UnavailableIteratorIndexException
	 *             if the index is unavailable, due to no specific ending given,
	 *             or the iterator has not yet started.
	 */
	public ModelKey getEndIndex() throws UnavailableIteratorIndexException;

}
