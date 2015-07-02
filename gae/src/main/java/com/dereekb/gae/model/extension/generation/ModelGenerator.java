package com.dereekb.gae.model.extension.generation;

import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for generating types using {@link ModelKey} keys.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ModelGenerator<T extends UniqueModel>
        extends Generator<T> {

	/**
	 * Generates a single model with the passed key.
	 *
	 * @param key
	 *            Key to attach to the model. Can be null.
	 * @return Generated model. Never null.
	 */
	public T generateModel(ModelKey key);

	/**
	 * Generates new models with the passed keys.
	 *
	 * @param keys
	 *            Keys to attach to the created models.
	 * @return Generated models. Never null.
	 */
	public List<T> generateModels(Iterable<ModelKey> keys);

}
