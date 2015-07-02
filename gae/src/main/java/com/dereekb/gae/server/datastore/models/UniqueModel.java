package com.dereekb.gae.server.datastore.models;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Interface for models that have unique database identifiers.
 *
 * @author dereekb
 * @param <T>
 *            Key/Identifier type of the model.
 * @see {@link Getter}
 */
public interface UniqueModel {

	/**
	 * @return the {@link ModelKey} for this type. Can be null.
	 */
	public ModelKey getModelKey();

}
