package com.dereekb.gae.server.datastore.models.keys;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Type with a self-generated key that can be regenerated.
 * 
 * @author dereekb
 *
 */
public interface SelfGeneratedIdentifier
        extends UniqueModel {

	/**
	 * Regenerates the identifier for this model.
	 * 
	 * @throws RuntimeException
	 *             thrown if the key could not be regenerated.
	 */
	public abstract void regenerateIdentifier() throws RuntimeException;

}
