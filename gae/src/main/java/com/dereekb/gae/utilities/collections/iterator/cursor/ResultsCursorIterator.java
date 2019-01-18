package com.dereekb.gae.utilities.collections.iterator.cursor;

import java.util.Iterator;

import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;

/**
 * {@link Iterator} that is keyed using a {@link ResultsCursor} values.
 * <p>
 * The cursors returned reference the start and end of the {@link Iterator}. The {@link Iterator} behaves like normal.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ResultsCursorIterator<T>
        extends Iterator<T> {

	/**
	 * Returns the cursor this iterator began at, if available. It is up to the
	 * implementation to determine if null or a "start" value cursor should be
	 * returned in cases where no explicit cursor was specified.
	 *
	 * @return {@link ResultsCursor}, or {@code null} if not available.
	 * @throws UnavailableIteratorIndexException
	 *             If the index is unavailable.
	 */
	public ResultsCursor getStartCursor() throws UnavailableIteratorIndexException;

	/**
	 * Returns the cursor this iterator ends at.
	 *
	 * @return {@link ResultsCursor}. Never {@code null}.
	 * @throws UnavailableIteratorIndexException
	 *             if the index is unavailable, due to no specific ending given,
	 *             or the iterator has not yet started.
	 */
	public ResultsCursor getEndCursor() throws UnavailableIteratorIndexException;

}
