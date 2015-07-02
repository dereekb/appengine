package com.dereekb.gae.server.datastore.objectify.components;

import com.dereekb.gae.server.datastore.Setter;
import com.googlecode.objectify.Key;

public interface ObjectifyKeyedSetter<T>
        extends Setter<T> {

	/**
	 * Deletes a model using a key.
	 *
	 * @param key
	 * @param async
	 *            Whether or not to save asynchronously.
	 */
	public void delete(Key<T> key,
	                   boolean async);

	/**
	 * Deletes an iterable list of models using their keys.
	 *
	 * @param keys
	 * @param async
	 *            Whether or not to save asynchronously.
	 */
	public void deleteWithObjectifyKeys(Iterable<Key<T>> keys,
	                                    boolean async);

}
