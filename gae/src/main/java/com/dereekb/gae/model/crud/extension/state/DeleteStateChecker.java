package com.dereekb.gae.model.crud.extension.state;

/**
 * Interface for checking the current delete state for an object.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface DeleteStateChecker<T> {

	/**
	 * @param object
	 *            Object to check delete status.
	 * @return {@code true} if the object can be deleted.
	 */
	public boolean canDelete(T object);

}
