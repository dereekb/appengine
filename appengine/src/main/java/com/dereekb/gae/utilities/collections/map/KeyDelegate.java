package com.dereekb.gae.utilities.collections.map;

import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;

/**
 * Reads keys from the input models.
 * 
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            model type
 */
public interface KeyDelegate<K, T> {

	/**
	 * Returns the key for the input model.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return Key. Never {@code null}.
	 * @throws NoModelKeyException
	 *             thrown if the model's key is not available.
	 */
	public K keyForModel(T model) throws NoModelKeyException;

}
