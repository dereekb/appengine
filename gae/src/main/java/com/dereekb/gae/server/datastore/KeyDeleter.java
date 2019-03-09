package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for deleting removing models by key.
 *
 * @author dereekb
 *
 * @see Setter
 * @see ModelDeleter
 */
public interface KeyDeleter {

	/**
	 * Deletes an entity by {@link ModelKey} synchronously.
	 *
	 * @param key
	 *            Key of model to delete.
	 */
	public void deleteWithKey(ModelKey key);

	/**
	 * Deletes entities using a list of {@link ModelKey} synchronously.
	 *
	 * @param keys
	 *            Iterable list of keys of model to delete.
	 */
	public void deleteWithKeys(Iterable<ModelKey> keys);

	/**
	 * Deletes an entity by {@link ModelKey}.
	 *
	 * @param key
	 *            Key of model to delete.
	 */
	public void deleteWithKeyAsync(ModelKey key);

	/**
	 * Deletes entities using a list of {@link ModelKey}.
	 *
	 * @param keys
	 *            Iterable list of keys of model to delete.
	 */
	public void deleteWithKeysAsync(Iterable<ModelKey> keys);

}
