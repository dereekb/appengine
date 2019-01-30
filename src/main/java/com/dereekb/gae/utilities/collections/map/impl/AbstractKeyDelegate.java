package com.dereekb.gae.utilities.collections.map.impl;

import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.utilities.collections.map.KeyDelegate;

/**
 * Abstract {@link KeyDelegate} implementation.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 */
public abstract class AbstractKeyDelegate<K, T>
        implements KeyDelegate<K, T> {

	// MARK: KeyDelegate
	@Override
	public K keyForModel(T model) throws NoModelKeyException {
		K key = this.readKeyFromModel(model);

		if (key == null) {
			throw new NoModelKeyException();
		}

		return key;
	}

	protected abstract K readKeyFromModel(T model);

}
