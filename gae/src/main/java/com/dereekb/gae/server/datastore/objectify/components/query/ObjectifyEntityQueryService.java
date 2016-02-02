package com.dereekb.gae.server.datastore.objectify.components.query;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.ConfiguredObjectifyQuery;
import com.googlecode.objectify.Key;

/**
 * Service for querying entities and keys.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyEntityQueryService<T extends ObjectifyModel<T>> {

	/**
	 * Retrieves models that meet the query parameters.
	 *
	 * @param query
	 *            {@link ConfiguredObjectifyQuery}. Never {@code null}.
	 * @return {@link List} of matching models.
	 */
	public List<T> queryModels(ConfiguredObjectifyQuery<T> query);

	/**
	 * Retrieves keys of the models that meet the query parameters.
	 *
	 * @param query
	 *            {@link ConfiguredObjectifyQuery}. Never {@code null}.
	 * @return {@link List} of keys of matching models.
	 */
	public List<Key<T>> queryKeys(ConfiguredObjectifyQuery<T> query);

}
