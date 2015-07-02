package com.dereekb.gae.server.datastore.objectify;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.googlecode.objectify.Key;

/**
 * Objectify stored model.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ObjectifyModel<T>
        extends UniqueModel {

	/**
	 * Returns the objectify key for this model.
	 * @return
	 */
	public Key<T> getObjectifyKey();

}
