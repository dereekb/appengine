package com.dereekb.gae.utilities.collections.iterator.cursor;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.iterator.index.exception.InvalidIteratorIndexException;

/**
 * {@link Iterable} value that can set the starting cursor.
 *
 * @author dereekb
 *
 * @param <T>
 *            Type to iterate.
 * @see ResultsCursorIterator
 */
public interface ResultsCursorIterable<T>
        extends Iterable<T> {

	/**
	 * Returns the cursor this {@link ResultsCursorIterable} begins at.
	 *
	 * @return {@link ResultsCursor} for the cursor to start at, or {@code null}
	 *         if one has not yet been set.
	 */
	public ResultsCursor getStartCursor();

	/**
	 * Sets the new start index value, or clears it if {@code null} is passed.
	 *
	 * @param index
	 *            {@link ModelKey} index. {@code null} to clear the current
	 *            value.
	 * @throws InvalidIteratorIndexException
	 *             if {@code index} is invalid.
	 */
	public void setStartCursor(ResultsCursor cursor) throws InvalidIteratorIndexException;

	/**
	 * Generates a new {@link ResultsCursorIterator} for this iterable.
	 *
	 * @return {@link ResultsCursorIterator}. The iterator's
	 *         {@link ResultsCursorIterator#getStartCursor()} should match the
	 *         value
	 *         from {@link #getStartCursor()}, if one has been set.
	 */
	@Override
	public ResultsCursorIterator<T> iterator();

}
