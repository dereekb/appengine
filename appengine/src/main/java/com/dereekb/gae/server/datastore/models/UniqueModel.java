package com.dereekb.gae.server.datastore.models;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.Keyed;

/**
 * Interface for models that are {@link Keyed} by a {@link ModelKey}.
 *
 * @author dereekb
 */
public interface UniqueModel
        extends Keyed<ModelKey> {

	/**
	 * @return the {@link ModelKey} for this type. Can be {@code null}.
	 */
	public ModelKey getModelKey();

}
