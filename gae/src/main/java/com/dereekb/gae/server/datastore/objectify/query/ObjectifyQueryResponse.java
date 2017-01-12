package com.dereekb.gae.server.datastore.objectify.query;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * Pre-configured Objectify query that can return its results as a
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryResponse<T extends ObjectifyModel<T>> {

	/**
	 * Checks whether or not any results exist.
	 * 
	 * @return {@code true} if one or more results are available.
	 */
	public boolean hasResults();
	
	/**
	 * Returns the created query.
	 *
	 * @return {@link SimpleQuery} used. Never {@code null}.
	 */
	public SimpleQuery<T> getQuery();

	/**
	 * Retrieves models that meet the query parameters.
	 *
	 * @return {@link List} of matching models.
	 */
	public List<T> queryModels();

	/**
	 * Retrieves {@link Key} values of the models that meet the query
	 * parameters.
	 *
	 * @return {@link List} of keys of matching models.
	 */
	public List<Key<T>> queryObjectifyKeys();

	/**
	 * Retrieves {@link ModelKey} values of the models that meet the query
	 * parameters.
	 *
	 * @return {@link List} of keys of matching models.
	 */
	public List<ModelKey> queryModelKeys();

	/**
	 * Retrieves a {@link QueryResultIterator} instance.
	 *
	 * @return {@link QueryResultIterator}. Never {@code null}.
	 */
	public QueryResultIterable<T> queryModelsIterable();

	/**
	 * Retrieves a {@link QueryResultIterator} instance for objectify keys.
	 *
	 * @return {@link QueryResultIterator}. Never {@code null}.
	 */
	public QueryResultIterable<Key<T>> queryObjectifyKeyIterable();

}
