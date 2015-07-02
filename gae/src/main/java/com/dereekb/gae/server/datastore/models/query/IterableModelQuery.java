package com.dereekb.gae.server.datastore.models.query;

import java.util.Map;

import com.dereekb.gae.utilities.factory.Factory;
import com.google.appengine.api.datastore.Cursor;

/**
 * Iterator (although it implements the {@link Iterable} interface) of the App Engine Datastore. Can set the cursor to resume from a
 * previous query.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public interface IterableModelQuery<T>
        extends Iterable<T>, Factory<ModelQueryIterator<T>> {

	/**
	 * Returns the cursor this iterator begins at.
	 * 
	 * @return The start cursor, or null if none has been set.
	 */
	public Cursor getStartCursor();

	/**
	 * 
	 * @param cursor Cursor for the iterator to start at.
	 */
	public void setStartCursor(Cursor cursor);

	/**
	 * Optional function for setting custom parameters for the query.
	 * 
	 * Ideally, the cursor should still be set using the setter.
	 * 
	 * @param parameters
	 */
	public void setCustomParameters(Map<String, String> parameters);

}
