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
public interface AtomicReadService<T extends UniqueModel> {

	/**
	 * Reads objects for their model keys.
	 *
	 * @param request
	 *            Collection of {@link ModelKey} instances.
	 * @return Collection of retrieved models.
	 * @throws AtomicOperationException
	 *             Occurs when not all objects requested can be read.
	 */
	public Collection<T> read(Collection<ModelKey> keys) throws AtomicOperationException;

}
