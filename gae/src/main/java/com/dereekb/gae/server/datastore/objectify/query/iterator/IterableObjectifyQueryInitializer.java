package com.dereekb.gae.server.datastore.objectify.query.iterator;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;

/**
 * Used by {@link IterableObjectifyQuery} to initialize the query used for
 * iteration.
 *
 * @author dereekb
 * @param <T>
 *            Model type.
 */
public interface IterableObjectifyQueryInitializer<T> {

	/**
	 * Initializes the target query.
	 *
	 * @param query
	 *            {@link ObjectifyQuery}. Never {@code null}.
	 * @param parameters
	 *            {@link Map} of optional parameters. Can be {@code null}.
	 */
	public void initializeQuery(ObjectifyQuery<T> query,
	                            Map<String, String> parameters);

}
