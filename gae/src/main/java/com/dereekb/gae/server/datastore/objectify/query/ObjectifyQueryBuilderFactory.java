package com.dereekb.gae.server.datastore.objectify.query;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Used for building new {@link ConfiguredObjectifyQuery} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryBuilderFactory<T extends ObjectifyModel<T>> {

	/**
	 * Creates a new {@link ObjectifyQueryBuilder}.
	 *
	 * @return {@link ObjectifyQueryBuilder}. Never {@code null}.
	 */
	public ObjectifyQueryBuilder<T> makeQuery();

	/**
	 * Creates a new {@link ObjectifyQueryBuilder} and initializes it with the
	 * input parameters.
	 *
	 * @return {@link ObjectifyQueryBuilder}. Never {@code null}.
	 */
	public ObjectifyQueryBuilder<T> makeQuery(Map<String, String> parameters);

}
