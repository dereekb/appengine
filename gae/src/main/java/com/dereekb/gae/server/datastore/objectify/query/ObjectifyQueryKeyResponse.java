package com.dereekb.gae.server.datastore.objectify.query;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.google.appengine.api.datastore.QueryResultIterator;
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
        extends ObjectifyQueryResponse {

	/**
	 * Returns the created query.
	 *
	 * @return {@link SimpleQuery} used. Never {@code null}.
	 */
	@Override
	public SimpleQuery<T> getQuery();

	/**
	 * Retrieves {@link ModelKey} values of the models that meet the query
	 * parameters.
	 *
	 * @return {@link List} of keys of matching models.
	 */
	public List<ModelKey> queryModelKeys();

	/**
	 * Retrieves {@link Key} values of the models that meet the query
	 * parameters.
	 *
	 * @return {@link List} of keys of matching models.
	 */
	public List<Key<T>> queryObjectifyKeys();

	/**
	 * Retrieves a raw {@link QueryResultIterator} instance for objectify keys.
	 *
	 * @return {@link QueryResultIterator}. Never {@code null}.
	 */
	public QueryResultIterator<Key<T>> queryObjectifyKeyIterator();

}
