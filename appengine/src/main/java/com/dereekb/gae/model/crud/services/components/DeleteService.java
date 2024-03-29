package com.dereekb.gae.model.crud.services.components;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Thread-safe service that deletes existing objects.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface DeleteService<T extends UniqueModel> {

	/**
	 * Deletes objects using the input request.
	 *
	 * @param request
	 *            {@link DeleteRequest}. Never {@code null}.
	 * @return {@link DeleteResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             thrown when not all existing objects can be deleted, or the
	 *             request specifies "atomic" and not all objects requested to
	 *             be deleted exist.
	 *             <p>
	 *             Does not get called if target models that are unavailable are
	 *             deemed to be already deleted.
	 */
	public DeleteResponse<T> delete(DeleteRequest request) throws AtomicOperationException;

}
