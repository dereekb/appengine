package com.dereekb.gae.server.datastore.objectify.core;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ObjectifyDatabaseEntityKeyEnforcement} enforcer.
 *
 * @author dereekb
 *
 */
public interface ObjectifyDatabaseEntityKeyEnforcer {

	/**
	 * Verifies whether or not the key on the model is allowed for storage.
	 *
	 * @param key
	 *            {@link ModelKey}. Can be {@code null}.
	 * @return {@code true} if allowed.
	 */
	public boolean isAllowedForStorage(ModelKey key);

}
