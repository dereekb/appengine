package com.dereekb.gae.server.datastore.objectify.query;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;

/**
 * Pre-configured Objectify query that can return its results as a
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ObjectifyQuery<T extends ObjectifyModel<T>> {

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
	public List<Key<T>> queryModelKeys();

	/**
	 * Retrieves a {@link QueryResultIterator} instance.
	 *
	 * @return {@link QueryResultIterator}. Never {@code null}.
	 */
	public QueryResultIterator<T> queryIterator();
}
