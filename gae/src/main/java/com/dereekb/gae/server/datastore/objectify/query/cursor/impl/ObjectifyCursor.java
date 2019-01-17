package com.dereekb.gae.server.datastore.objectify.query.cursor.impl;

import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link ResultsCursor} implementation that wraps a {@link Cursor}.
 *
 * @author dereekb
 *
 */
public class ObjectifyCursor
        implements ResultsCursor {

	private Cursor cursor;
	private transient String cursorString;

	public ObjectifyCursor(String webSafeString) {
		this.setCursor(Cursor.fromWebSafeString(webSafeString));
	}

	public ObjectifyCursor(Cursor cursor) {
		this.setCursor(cursor);
	}

	public static ObjectifyCursor make(Cursor cursor) {
		return new ObjectifyCursor(cursor);
	}

	public static ObjectifyCursor make(ResultsCursor cursor) {
		ObjectifyCursor objectifyCursor = null;

		if (cursor != null) {
			objectifyCursor = make(cursor.getCursorString());
		}

		return objectifyCursor;
	}

	public static ObjectifyCursor make(String webSafeString) {
		return new ObjectifyCursor(webSafeString);
	}

	public Cursor getCursor() {
		return this.cursor;
	}

	public void setCursor(Cursor cursor) {
		if (cursor == null) {
			throw new IllegalArgumentException("cursor cannot be null.");
		}

		this.cursor = cursor;
		this.cursorString = null;
	}

	// MARK: IndexedModelCursor
	@Override
	public String getCursorString() {
		if (this.cursorString == null) {
			this.cursorString = this.cursor.toWebSafeString();
		}

		return this.cursorString;
	}

	@Override
	public String toString() {
		return "ObjectifyCursor [cursor=" + this.cursor + "]";
	}

}
