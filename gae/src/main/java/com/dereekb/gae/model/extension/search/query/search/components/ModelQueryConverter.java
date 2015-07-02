package com.dereekb.gae.model.extension.search.query.search.components;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;

/**
 * Used to convert queries into {@link ObjectifyQuery} instances.
 *
 * @author dereekb
 *
 * @param <Q>
 */
public interface ModelQueryConverter<T, Q> {

	/**
	 * Generates a {@link ObjectifyQuery} instance from the input query.
	 */
	public ObjectifyQuery<T> convertQuery(Q query);

}
