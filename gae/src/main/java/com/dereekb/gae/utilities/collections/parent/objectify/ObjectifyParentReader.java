package com.dereekb.gae.utilities.collections.parent.objectify;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;

/**
 * Used for loading and iterating up the parent chain.
 *
 * @author dereekb
 *
 */
public interface ObjectifyParentReader<T extends ObjectifyModel<T>> {

	/**
	 * Iterates through the parent chain and returns all parent models.
	 *
	 * @return {@link List} of all parent models.
	 */
	public List<T> getParentModels(T child);

}
