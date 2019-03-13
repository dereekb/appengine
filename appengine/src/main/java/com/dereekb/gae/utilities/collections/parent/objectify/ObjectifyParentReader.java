package com.dereekb.gae.utilities.collections.parent.objectify;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for loading and iterating up the parent chain.
 *
 * @author dereekb
 *
 */
public interface ObjectifyParentReader<T extends ObjectifyChild<T>> {

	/**
	 * Iterates through the parent chain and returns all parent models.
	 *
	 * @return {@link List} of all parent models.
	 */
	public List<T> getParentModels(T child);

	/**
	 * Loads the child and retrieves the set of {@link ModelKey} values for that
	 * type.
	 *
	 * @param childKey
	 * @return
	 * @throws UnavailableModelException
	 */
	public Set<ModelKey> getParentKeys(ModelKey childKey) throws UnavailableModelException;

	/**
	 *
	 * @param target
	 * @return
	 */
	public Set<ModelKey> getParentKeys(T target);

}
