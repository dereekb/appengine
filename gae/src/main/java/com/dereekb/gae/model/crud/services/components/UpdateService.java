package com.dereekb.gae.model.crud.services.components;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Service that updates existing objects.
 *
 * Should be a thread-safe implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type that implements the {@link UniqueModel} interface.
 */
public interface UpdateService<T extends UniqueModel> {

	/**
	 * Updates objects.
	 *
	 * @param request
	 * @return {@link UpdateResponse} instance.
	 * @throws AtomicOperationException
	 *             Occurs when not all objects requested can be updated.
	 */
	public UpdateResponse<T> update(UpdateRequest<T> request) throws AtomicOperationException;

}
