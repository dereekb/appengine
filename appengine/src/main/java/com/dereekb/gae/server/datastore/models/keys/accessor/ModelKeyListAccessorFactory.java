package com.dereekb.gae.server.datastore.models.keys.accessor;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Factory for creating new {@link ModelKeyListAccessor} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelKeyListAccessorFactory<T extends UniqueModel>
        extends TypedModel {

	/**
	 * Creates a new accessor.
	 *
	 * @return {@link ModelKeyListAccessor}. Never {@code null}.
	 */
	public ModelKeyListAccessor<T> createAccessor();

	/**
	 * Creates a new accessor with the input keys.
	 *
	 * @param keys
	 *            {@link Collection}. Never {@code null}.
	 *
	 * @return {@link ModelKeyListAccessor}. Never {@code null}.
	 */
	public ModelKeyListAccessor<T> createAccessor(Collection<ModelKey> keys);

	/**
	 * Creates a new accessor with the input key strings.
	 * <p>s
	 * Keys are converted to {@link ModelKey} properly.
	 *
	 * @param keys
	 *            {@link Collection}. Never {@code null}.
	 *
	 * @return {@link ModelKeyListAccessor}. Never {@code null}.
	 */
	public ModelKeyListAccessor<T> createAccessorWithStringKeys(Collection<String> stringKeys);

	/**
	 * Creates a new accessor with the input models.
	 *
	 * @param models
	 *            {@link Collection}. Never {@code null}.
	 *
	 * @return {@link ModelKeyListAccessor}. Never {@code null}.
	 */
	public ModelKeyListAccessor<T> createAccessorWithModels(Collection<T> models);

}
