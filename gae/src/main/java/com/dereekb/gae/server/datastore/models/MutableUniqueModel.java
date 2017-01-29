package com.dereekb.gae.server.datastore.models;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.exception.InvalidModelKeyTypeException;

/**
 * {@link UniqueModel} that can have its {@link ModelKey} changed.
 * 
 * @author dereekb
 *
 */
public interface MutableUniqueModel
        extends UniqueModel {

	/**
	 * Sets the model key.
	 * 
	 * @param key
	 *            {@link ModelKey}. Can be {@code null}.
	 * @throws InvalidModelKeyTypeException
	 *             thrown if the expected type is invalid.
	 */
	public void setModelKey(ModelKey key) throws InvalidModelKeyTypeException;

}
