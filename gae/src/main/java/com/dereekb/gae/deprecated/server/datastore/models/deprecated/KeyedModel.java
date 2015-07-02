package com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated;

import com.thevisitcompany.gae.server.datastore.Getter;
import com.thevisitcompany.gae.server.datastore.models.UniqueModel;

/**
 * Interface for models that have a unique identifier of the given type.
 *
 * @author dereekb
 * @param <T>
 *            Key/Identifier type of the model.
 * @see {@link Getter}
 * @deprecated Replaced by {@link UniqueModel}.
 */
@Deprecated
public interface KeyedModel<T> {

	/**
	 * @return Returns the unique identifier of this model. May return null if no key has been set for this model.
	 */
	public T getId();

}
