package com.dereekb.gae.model.crud.services.components;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Thread-safe service that updates existing objects.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateService<T extends UniqueModel> {

	/**
	 * Updates objects using the input request.
	 *
	 * @param request
	 *            {@link UpdateRequest}. Never {@code null}.
	 * @return {@link UpdateResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             Occurs when not all objects requested can be updated.
	 */
	public UpdateResponse<T> update(UpdateRequest<T> request) throws AtomicOperationException;

}
