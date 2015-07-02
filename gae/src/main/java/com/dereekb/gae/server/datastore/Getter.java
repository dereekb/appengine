package com.dereekb.gae.server.datastore;

import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Interface for retrieving {@link UniqueModel} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model that implements the {@link UniqueModel} interface.
 * @see {@link Setter} for saving and deleting models from a source.
 */
public interface Getter<T extends UniqueModel> {

	/**
	 * Checks that the model exists.
	 *
	 * @param key
	 *            Identifier of the model.
	 * @return True if the object exists, false otherwise.
	 */
	public boolean exists(ModelKey key);

	/**
	 * Checks that all the target models exist.
	 *
	 * @param keys
	 *            Iterable list of keys.
	 * @return True if all objects exist with the specified keys.
	 */
	public boolean allExist(Iterable<ModelKey> keys);

	/**
	 * Returns a set of keys of objects that exist in the database with the
	 * input keys.
	 *
	 * @param keys
	 *            Iterable list of keys.
	 * @return Set of keys that correspond to existing items.
	 */
	public Set<ModelKey> exists(Iterable<ModelKey> keys);

	/**
	 * Retrieves the database model that corresponds to the input key.
	 *
	 * @param key
	 *            Identifier of the model.
	 * @return Model that correponds to the input key, or null if no model
	 *         exists with that key.
	 */
	public T get(ModelKey key);

	/**
	 * Retrieves the database copy of the input model.
	 *
	 * @param model
	 *            Single model.
	 * @return Model read from the source, or null if it does not exist, or the
	 *         input model has a null key.
	 */
	public T get(T model);

	/**
	 * Retrieves database copies of the specified models. Models that don't have
	 * an key are ignored.
	 *
	 * @param models
	 *            Iterable list of models.
	 * @return A list of models that could be read from the source.
	 */
	public List<T> get(Iterable<T> models);

	/**
	 * Retrieves items with the given keys.
	 *
	 * @param keys
	 *            Iterable list of keys.
	 * @return A list of models that could be read from the source.
	 */
	public List<T> getWithKeys(Iterable<ModelKey> keys);

}
