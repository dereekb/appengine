package com.dereekb.gae.server.datastore.objectify.query.iterator;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.google.appengine.api.datastore.Cursor;

/**
 * Used for generating {@link ObjectifyQueryIterable} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IterableObjectifyQuery<T extends ObjectifyModel<T>> {

	public ObjectifyQueryIterable<T> makeIterable();

	public ObjectifyQueryIterable<T> makeIterable(Cursor cursor);

	public ObjectifyQueryIterable<T> makeIterable(Cursor startCursor,
	                                              Map<String, String> parameters);

}
