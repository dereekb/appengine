package com.dereekb.gae.server.datastore.models.keys.utility;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.model.source.Source;

/**
 * {@link Source} for {@link ModelKey}.
 * 
 * @author dereekb
 *
 */
public interface ModelKeySource
        extends Source<ModelKey> {

	/**
	 * Loads the model key.
	 * 
	 * @return {@link ModelKey}. Never {@code null}.
	 * @throws NoModelKeyException
	 *             if the key is not available.
	 */
	public ModelKey loadModelKey() throws NoModelKeyException;

}
