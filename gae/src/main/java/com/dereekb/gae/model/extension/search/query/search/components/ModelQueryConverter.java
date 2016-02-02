package com.dereekb.gae.model.extension.search.query.search.components;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.ConfiguredObjectifyQuery;

/**
 * Used to convert queries into {@link ConfiguredObjectifyQuery} instances.
 *
 * @author dereekb
 *
 * @param <Q>
 *            query type
 */
public interface ModelQueryConverter<T extends ObjectifyModel<T>, Q> {

	/**
	 * Generates a {@link ConfiguredObjectifyQuery} instance from the input query.
	 */
	public ConfiguredObjectifyQuery<T> convertQuery(Q query);

}
