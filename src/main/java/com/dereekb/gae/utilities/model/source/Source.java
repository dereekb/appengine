package com.dereekb.gae.utilities.model.source;

import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;

/**
 * Source for an object.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Source<T> {

	/**
	 * Loads the object.
	 * 
	 * @return {@link Object}. Never {@code null}.
	 * @throws RuntimeException
	 *             thrown if an exception occurs while trying to load.
	 * @throws UnavailableSourceObjectException
	 *             thrown if the object cannot be loaded.
	 */
	public T loadObject() throws RuntimeException, UnavailableSourceObjectException;

}
