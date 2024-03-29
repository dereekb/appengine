package com.dereekb.gae.model.extension.visibility.service;

import com.dereekb.gae.deprecated.model.extension.visibility.VisibilityState;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;

/**
 * Interface that allows changing the visibility of models through this indexService.
 *
 * @author dereekb
 *
 */
public interface VisibilityService<T> {

	/**
	 * Changes the visibility of the models. Changes in the models may not be
	 * reflected immediately.
	 *
	 * Visibility changes are idempotent.
	 *
	 * @param models
	 *            Models to change.
	 * @param state
	 *            State to change all the models to.
	 * @throws AtomicOperationException
	 *             Thrown if or more models could not be modified.
	 */
	public void changeVisibility(Iterable<T> models,
	                             VisibilityState state) throws AtomicOperationException;

}
