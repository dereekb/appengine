package com.dereekb.gae.model.crud.services.components;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Simple version of {@link ReadService} for atomically reading existing
 * objects.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface AtomicReadService<T extends UniqueModel>
        extends ReadService<T> {

	/**
	 * Reads the object for the input model key.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @return Read model. Never {@code null}.
	 * @throws AtomicOperationException
	 *             Thrown if the model cannot be read.
	 */
	public T read(ModelKey key) throws AtomicOperationException;

	/**
	 * Reads objects for their model keys.
	 *
	 * @param request
	 *            Collection of {@link ModelKey} instances.
	 * @return {@link Collection} of retrieved models. Never {@code null}.
	 * @throws AtomicOperationException
	 *             Thrown when not all objects requested can be read.
	 */
	public Collection<T> read(Collection<ModelKey> keys) throws AtomicOperationException;

}
