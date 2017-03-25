package com.dereekb.gae.server.datastore.objectify.components;

import com.dereekb.gae.server.datastore.Setter;
import com.googlecode.objectify.Key;

/**
 * {@link Setter} extension for Objectify.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyKeyedSetter<T>
        extends Setter<T> {

	/**
	 * Synchronously deletes a model using a key.
	 *
	 * @param key
	 *            Objectify Key. Never {@code null}.
	 */
	public void delete(Key<T> key);

	/**
	 * Synchronously deletes an iterable list of models using their keys.
	 *
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 */
	public void deleteWithObjectifyKeys(Iterable<Key<T>> keys);

	/**
	 * Deletes a model using a key.
	 *
	 * @param key
	 *            Objectify Key. Never {@code null}.
	 */
	public void deleteAsync(Key<T> key);

	/**
	 * Deletes an iterable list of models using their keys.
	 *
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 */
	public void deleteWithObjectifyKeysAsync(Iterable<Key<T>> keys);

}
