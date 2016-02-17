package com.dereekb.gae.model.extension.visibility.service;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.visibility.VisibilityState;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Interface that allows changing the visibility of models through this indexService.
 *
 * @author dereekb
 *
 */
public interface KeyedVisibilityService {

	/**
	 * Changes the visibility of the models for each of the keys. The visibility
	 * change may not be reflected immediately.
	 *
	 * Visibility changes are indempotent.
	 *
	 * @param keys
	 *            Keys of models to change.
	 * @param state
	 *            State to change all the models to.
	 * @throws AtomicOperationException
	 *             Thrown if the model for one of the keys cannot be retrieved,
	 *             or one or more model cannot be modified.
	 */
	public void changeVisibility(Iterable<ModelKey> keys,
	                             VisibilityState state) throws AtomicOperationException;

}
