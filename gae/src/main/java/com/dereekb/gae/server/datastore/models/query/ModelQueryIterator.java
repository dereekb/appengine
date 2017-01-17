package com.dereekb.gae.server.datastore.models.query;

import java.util.Iterator;

import com.google.appengine.api.datastore.Cursor;

/**
 * Special {@link Iterator} used by
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelQueryIterator<T>
        extends Iterator<T> {

	/**
	 * Returns the cursor this iterator begins at.
	 *
	 * @return The start cursor, or null if none has been set.
	 */
	public Cursor getStartCursor();

	/**
	 * Returns the current cursor this iterator is on.
	 *
	 * @return The current cursor, or null if the iterator has not started yet.
	 */
	public Cursor getCurrentCursor();

	/**
	 * Returns the cursor this iterator ended on.
	 *
	 * @return Returns the cursor this iterator ended on. Is {@code null} if the
	 *         iterator has not started running.
	 */
	public Cursor getEndCursor();

}
