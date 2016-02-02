package com.dereekb.gae.server.datastore.objectify.components.query;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.ConfiguredObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.iterator.IterableObjectifyQuery;
import com.google.appengine.api.datastore.QueryResultIterator;


public interface ObjectifyIterationQueryService<T extends ObjectifyModel<T>> {

	/**
	 * Creates a new {@link IterableObjectifyQuery} instance.
	 */
	public IterableObjectifyQuery<T> makeIterableQuery();

	/**
	 * Retrieves raw query results, used for iterating.
	 *
	 * @param query
	 *            {@link ConfiguredObjectifyQuery}. Never {@code null}.
	 * @return {@link QueryResultIterator} for the input query.
	 */
	public QueryResultIterator<T> queryIterator(ConfiguredObjectifyQuery<T> query);

}
