package com.dereekb.gae.server.datastore.objectify.components.query;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;

public interface ObjectifyExistenceQueryService<T extends ObjectifyModel<T>> {

	/**
	 * Queries the datastore to check that all the specified models exist.
	 *
	 * @param keys
	 * @return True if all models exist in the datastore.
	 */
	public Boolean modelsExist(Collection<Key<T>> keys);

	/**
	 * Checks the datastore with the given keys to see if a model exists that
	 * corresponds to that key.
	 *
	 * @param keys
	 *            {@link Collection} of keys to check.
	 * @return Set of keys that have a model representation in the datastore.
	 */
	public Set<Key<T>> filterExistingModels(Collection<Key<T>> keys);

}
