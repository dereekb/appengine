package com.dereekb.gae.server.datastore.models;

import com.dereekb.gae.model.extension.read.exception.UnavailableTypesException;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveEntryContainer;

/**
 * {@link CaseInsensitiveEntryContainer} extension for {@link TypedModel}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface TypedModelMap<T>
        extends CaseInsensitiveEntryContainer<T> {

	/**
	 * Returns the entry for the type matching the input model, if it exists.
	 *
	 * @param type
	 *            {@link TypedModel}. Never {@code null}.
	 * @return Entry. Never {@code null}.
	 * @throws RuntimeException
	 *             Thrown if the type does not exist.
	 *             The exception thrown by the container varies between
	 *             implementations.
	 */
	public T getEntryForType(TypedModel model) throws RuntimeException;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getEntryForType(String type) throws UnavailableTypesException;

}
