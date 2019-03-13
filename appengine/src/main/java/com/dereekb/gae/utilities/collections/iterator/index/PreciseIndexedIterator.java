package com.dereekb.gae.utilities.collections.iterator.index;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;

/**
 * {@link IndexedIterator} extension that provides more specific information
 * about the exact index.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface PreciseIndexedIterator<T>
        extends IndexedIterator<T> {

	/**
	 * Returns the index this iterator currently at.
	 *
	 * @return {@link ModelKey} containing the current index. Never {@code null}
	 * @throws UnavailableIteratorIndexException
	 *             If the index is unavailable.
	 */
	public ModelKey getCurrentIndex() throws UnavailableIteratorIndexException;

}
