package com.dereekb.gae.model.crud.extension.delete.state;

/**
 * Interface for checking the current delete state for an object.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface DeleteStateChecker<T> {

	/**
	 * @param object
	 *            Object to check delete status.
	 * @return true if the object can be deleted, false if it cannot be deleted.
	 */
	public boolean canDelete(T object);

}
