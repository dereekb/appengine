package com.dereekb.gae.utilities.collections.iterator.cursor;

/**
 * Generic model query cursor.
 *
 * @author dereekb
 *
 */
public interface ResultsCursor {

	/**
	 * Returns a safe string version of the cursor.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getCursorString();

}
