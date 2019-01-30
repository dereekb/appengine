package com.dereekb.gae.server.datastore.objectify;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.googlecode.objectify.Key;

/**
 * Objectify stored model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyModel<T>
        extends UniqueModel {

	/**
	 * Returns the Objectify key for this model.
	 * 
	 * @return {@link Key}. {@code null} if no identifier.
	 */
	public Key<T> getObjectifyKey();

}
