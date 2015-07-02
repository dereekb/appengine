package com.dereekb.gae.model.extension.visibility;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;

/**
 * Used to modify the visibility of input models.
 *
 * @author dereekb
 */
public interface VisibilityStateModifier<T> {

	/**
	 * Sets the states for the input models.
	 *
	 * State changes may not be reflected in the models immediately.
	 *
	 * @param models
	 *            Models to modify.
	 * @param state
	 *            State to set the models to.
	 * @throws AtomicOperationException
	 *             if any of the models cannot be changed.
	 */
	public void setState(Collection<T> models,
	                     VisibilityState state) throws AtomicOperationException;

}
