package com.dereekb.gae.model.crud.services.components;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Service that creates new objects.
 *
 * Should be a thread-safe implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type that implements the {@link UniqueModel} interface.
 */
public interface CreateService<T extends UniqueModel> {

	/**
	 * Creates new objects.
	 *
	 * @param request
	 * @return {@link CreateResponse} instance.
	 * @throws AtomicOperationException
	 *             Occurs when not all objects can be created.
	 */
	public CreateResponse<T> create(CreateRequest<T> request) throws AtomicOperationException;

}
