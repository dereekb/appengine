package com.dereekb.gae.model.crud.services.components;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Service that deletes existing objects.
 *
 * Should be a thread-safe implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type that implements the {@link UniqueModel} interface.
 */
public interface DeleteService<T extends UniqueModel> {

	/**
	 * Deletes objects.
	 *
	 * @param request
	 * @return {@link DeleteResponse} instance.
	 * @throws AtomicOperationException
	 *             Occurs when not all existing objects can be deleted, or the
	 *             request specifies "atomic" and not all objects requested to
	 *             be deleted exist.
	 *
	 *             Does not get called if target models that are unavailable are
	 *             deemed to be already deleted.
	 */
	public abstract DeleteResponse<T> delete(DeleteRequest<T> request) throws AtomicOperationException;

}
