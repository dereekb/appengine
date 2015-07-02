package com.dereekb.gae.server.datastore.objectify.components;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;

/**
 *
 * @author dereekb
 *
 */
public interface ObjectifyKeyedQuery<T> {

	/**
	 * Queries the datastore to check that all the specified models exist.
	 *
	 * @param keys
	 * @return True if all models exist in the datastore.
	 */
	public Boolean modelsExist(Collection<Key<T>> keys);

	/**
	 * Checks the datastore with the given keys to see if a model exists that corresponds to that key.
	 *
	 * @param keys
	 *            List of keys to check.
	 * @return List of keys that have a model representation in the datastore.
	 */
	public List<Key<T>> filterExistingModels(Collection<Key<T>> keys);

	/**
	 * Creates a new, default query to use.
	 *
	 * @return New Query
	 */
	public ObjectifyQuery<T> defaultQuery();

	/**
	 * Retrieves models that meet the query parameters.
	 *
	 * @param query
	 * @return List of matching models.
	 */
	public List<T> queryEntities(ObjectifyQuery<T> query);

	/**
	 * Retrieves keys of the models that meet the query parameters.
	 *
	 * @param query
	 * @return List of keys of matching models.
	 */
	public List<Key<T>> queryKeys(ObjectifyQuery<T> query);

	/**
	 * Retrieves raw query results, which are used for iterating.
	 */
	public QueryResultIterator<T> queryIterator(ObjectifyQuery<T> query);

}
