package com.dereekb.gae.server.datastore.models;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link TypedModel} extension that also has key type information.
 *
 */
public interface KeyTypedModel
        extends TypedModel {

	/**
	 * Returns the model key type for this model.
	 *
	 * @return {@link ModelKeyType}. Never {@code null}.
	 */
	public ModelKeyType getModelKeyType();

}
