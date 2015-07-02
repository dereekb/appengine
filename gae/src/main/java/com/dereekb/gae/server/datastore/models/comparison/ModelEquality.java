package com.dereekb.gae.server.datastore.models.comparison;

/**
 * Used for models since equality relies on HashCode and equals functions using only the identifier.
 *
 * @author dereekb
 */
@Deprecated
public interface ModelEquality<T> {

	/**
	 * Checks whether two models are equivalent or not.
	 *
	 * Should not include identifier and other supporting values, but only the main values that make up the model.
	 *
	 * @param object
	 * @return
	 */
	public boolean modelEquals(T object);

}
