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
 *            model type
 */
public interface ModelGenerator<T extends UniqueModel>
        extends Generator<T> {

	/**
	 * Returns the type name as a String.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getTypeName();

	/**
	 * Generates a new model key.
	 * 
	 * @return {@link ModelKey}. Never {@code null}.
	 */
	public ModelKey generateKey();

	/**
	 * Generates a single model with the passed key. The key may also be used
	 * for randomization purposes.
	 *
	 * @param key
	 *            Key to attach to the model. Can be null.
	 * @return Generated model. Never null.
	 */
	public T generateModel(ModelKey key);

	/**
	 * Generates a new unique model that has no key attached.
	 */
	public T generateModelWithoutKey();

	/**
	 * Generates new models with the passed keys. The keys may also be used for
	 * randomization purposes.
	 *
	 * @param keys
	 *            Keys to attach to the created models.
	 * @return Generated models. Never null.
	 */
	public List<T> generateModels(Iterable<ModelKey> keys);

}
