package com.dereekb.gae.server.datastore.objectify.components;

import java.util.List;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;

public interface ObjectifyKeyedGetter<T extends ObjectifyModel<T>>
        extends Getter<T> {

	/**
	 * Checks to see if a model with the specified key exists.
	 *
	 * @param key
	 * @return True if a model that corresponds to the key exists.
	 */
	public boolean exists(Key<T> key);

	/**
	 * Gets a model with the specified key.
	 *
	 * @param key
	 * @return Model of type <T> that corresponds to the key, if it exists.
	 */
	public T get(Key<T> key);

	/**
	 * Gets the set of models given the list of Objectify {@link Key} instances.
	 *
	 * @param keys
	 * @return Models of type <T> that correspond to the keys given.
	 */
	public List<T> getWithObjectifyKeys(Iterable<Key<T>> keys);

}
