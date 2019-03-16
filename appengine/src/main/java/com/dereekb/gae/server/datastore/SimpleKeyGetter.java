package com.dereekb.gae.server.datastore;

import java.util.List;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Simple {@link Getter} that is keys only.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SimpleKeyGetter<T>
        extends TypedModel {

	/**
	 * Retrieves the database model that corresponds to the input key.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @return Model, or {@code null} if it doesn't exist.
	 */
	public T get(ModelKey key) throws IllegalArgumentException;

	/**
	 * Retrieves items with the given keys.
	 *
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 * @return A list of models that could be read from the source.
	 */
	public List<T> getWithKeys(Iterable<ModelKey> keys);

}
