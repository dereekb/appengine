package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.Deleter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Acts as a pre-configured {@link Deleter} that is already configured to delete
 * synchronously or asynchronously.
 *
 * @author dereekb
 * @see {@link Deleter}
 */
public interface ConfiguredDeleter
        extends Deleter {

	public void deleteWithKey(ModelKey key);

	public void deleteWithKeys(Iterable<ModelKey> keys);

}
