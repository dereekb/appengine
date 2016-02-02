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
public interface ObjectifyQueryIterableFactory<T extends ObjectifyModel<T>> {

	public static final Integer MAX_ITERATION_LIMIT = 1000;

	public ObjectifyQueryIterable<T> makeIterable();

	public ObjectifyQueryIterable<T> makeIterable(Cursor cursor);

	public ObjectifyQueryIterable<T> makeIterable(Cursor cursor,
	                                              Map<String, String> parameters);

}
