package com.dereekb.gae.utilities.collections.parent.objectify;

import java.util.List;

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

}
