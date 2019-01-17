package com.dereekb.gae.server.datastore.objectify.query;

import java.util.List;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryKeyResponse;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;

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
