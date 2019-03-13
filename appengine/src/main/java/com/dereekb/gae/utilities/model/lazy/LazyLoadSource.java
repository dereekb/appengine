package com.dereekb.gae.utilities.model.lazy;

import com.dereekb.gae.utilities.model.source.Source;

/**
 * Lazy loading interface for a model.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type.
 */
public interface LazyLoadSource<T>
        extends Source<T> {

	/**
	 * Whether or not the implementation has tried loading the model yet.
	 * 
	 * @return {@code true} if has not tried loading.
	 */
	public boolean hasTriedLoading();

	/**
	 * Whether or not the model is non-null.
	 * <p>
	 * This won't call {@link #loadObject()} or attempt to load the model.
	 * 
	 * @return {@code true} if model is loaded and not-null.
	 */
	public boolean hasModel();

	/**
	 * Gets the model. If {@link #hasTriedLoading()} is false, the
	 * implementation will attempt to load the model.
	 * 
	 * @return Model, or {@code null} if it couldn't be loaded.
	 * @throws RuntimeException
	 *             thrown if an exception occurs while trying to load.
	 */
	public T safeLoadObject() throws RuntimeException;

}
