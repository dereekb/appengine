package com.dereekb.gae.server.datastore.models.query;

import java.util.Iterator;

import com.google.appengine.api.datastore.Cursor;

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
	public Cursor getCursor();

	/**
	 * 
	 * @return Returns the cursor this iterator ended on. Is null if the iterator has not started running.
	 */
	public Cursor getEndCursor();

}
