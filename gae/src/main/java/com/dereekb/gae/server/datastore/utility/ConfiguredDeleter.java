package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.KeyDeleter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Acts as a pre-configured {@link KeyDeleter} that is already configured to delete
 * synchronously or asynchronously.
 *
 * @author dereekb
 */
@Deprecated
public interface ConfiguredDeleter
        extends KeyDeleter {

	@Override
	public void deleteWithKey(ModelKey key);

	@Override
	public void deleteWithKeys(Iterable<ModelKey> keys);

}
