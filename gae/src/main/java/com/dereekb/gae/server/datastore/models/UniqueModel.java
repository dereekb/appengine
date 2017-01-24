package com.dereekb.gae.server.datastore.models;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.Keyed;

/**
 * Interface for models that have unique database identifiers.
 *
 * @author dereekb
 * @param <T>
 *            Key/Identifier type of the model.
 * @see {@link Getter}
 */
public interface UniqueModel
        extends Keyed<ModelKey> {

	/**
	 * @return the {@link ModelKey} for this type. Can be {@code null}.
	 */
	public ModelKey getModelKey();

}
