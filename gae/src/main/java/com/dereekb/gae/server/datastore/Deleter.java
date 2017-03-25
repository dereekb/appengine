package com.dereekb.gae.server.datastore;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for deleting removing models by key.
 *
 * @author dereekb
 *
 * @see Setter
 */
public interface Deleter {

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
	 * @param async
	 *            Whether or not to save asynchronously.
	 */
	public void deleteWithKey(ModelKey key,
	                          Boolean async);

	/**
	 * Deletes entities using a list of {@link ModelKey}.
	 *
	 * @param keys
	 *            Iterable list of keys of model to delete.
	 * @param async
	 *            Whether or not to save asynchronously.
	 */
	public void deleteWithKeys(Iterable<ModelKey> keys,
	                           Boolean async);

}
