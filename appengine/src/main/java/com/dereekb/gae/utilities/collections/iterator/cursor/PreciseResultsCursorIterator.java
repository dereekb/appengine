package com.dereekb.gae.utilities.collections.iterator.cursor;

import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;

/**
 * {@link ResultsCursorIterator} extension that provides information about the
 * current cursor.
 *
 * @author dereekb
 *
 */
public interface PreciseResultsCursorIterator<T>
        extends ResultsCursorIterator<T> {

	/**
	 * Returns the cursor this iterator is currently at.
	 *
	 * @return {@link ResultsCursor}. Never {@code null}.
	 * @throws UnavailableIteratorIndexException
	 *             If the index is unavailable.
	 */
	public ResultsCursor getCurrentCursor() throws UnavailableIteratorIndexException;

}
