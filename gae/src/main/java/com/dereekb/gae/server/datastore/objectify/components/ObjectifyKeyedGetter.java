package com.dereekb.gae.server.datastore.objectify.components;

import java.util.List;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;

/**
 * {@link Getter} extension that allows retrieve of models using Objectify
 * {@link Key} values.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyKeyedGetter<T extends ObjectifyModel<T>>
        extends Getter<T> {

	/**
	 * Checks to see if a model with the specified key exists.
	 *
	 * @param key
	 *            {@link Key}. Never {@code null}.
	 * @return {@code true} if the model exists.
	 */
	public boolean exists(Key<T> key);

	/**
	 * Gets a model with the specified key.
	 *
	 * @param key
	 *            {@link Key}. Never {@code null}.
	 * 
	 * @return Model if it exists, or {@code null}.
	 */
	public T get(Key<T> key);

	/**
	 * Gets the set of models given the list of Objectify {@link Key} instances.
	 *
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link List} of models. Never {@code null}.
	 */
	public List<T> getWithObjectifyKeys(Iterable<Key<T>> keys);

}
