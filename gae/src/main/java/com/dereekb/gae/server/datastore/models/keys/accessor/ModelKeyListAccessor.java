package com.dereekb.gae.server.datastore.models.keys.accessor;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Accessor that provides access to models, their {@link ModelKey} values, and
 * the model type.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelKeyListAccessor<T> {

	/**
	 * Returns the model type.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getModelType();

	/**
	 * Returns the keys for the models.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelKey> getModelKeys();

	/**
	 * Loads the models.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<T> getModels();

}
