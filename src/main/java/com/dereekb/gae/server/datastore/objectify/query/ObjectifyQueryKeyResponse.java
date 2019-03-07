package com.dereekb.gae.server.datastore.objectify.query;

import java.util.List;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryKeyResponse;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

import com.google.cloud.datastore.QueryResults;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * {@link ObjectifyQueryResponse} extension that provides actual key results.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryKeyResponse<T extends ObjectifyModel<T>>
        extends IndexedModelQueryKeyResponse, ObjectifyQueryResponse {

	/**
	 * Returns the created query.
	 *
	 * @return {@link SimpleQuery} used. Never {@code null}.
	 */
	@Override
	public SimpleQuery<T> getQuery();

	/**
	 * Retrieves {@link Key} values of the models that meet the query
	 * parameters.
	 *
	 * @return {@link List} of keys of matching models.
	 */
	public List<Key<T>> queryObjectifyKeys();

	/**
	 * Retrieves a raw {@link QueryResults} instance for objectify keys.
	 *
	 * @return {@link QueryResults}. Never {@code null}.
	 */
	public QueryResults<Key<T>> queryObjectifyKeyIterator();

}
