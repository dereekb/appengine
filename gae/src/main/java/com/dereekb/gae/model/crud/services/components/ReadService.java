package com.dereekb.gae.model.crud.services.components;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Thread-safe service for reading existing objects.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ReadService<T extends UniqueModel> {

	/**
	 * Reads objects using the input request.
	 *
	 * @param request
	 *            {@link ReadRequest}. Never {@code null}.
	 * @return {@link ReadResponse}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             Occurs when the request specifies "atomic" and not all
	 *             objects requested can be read.
	 */
	public ReadResponse<T> read(ReadRequest request) throws AtomicOperationException;

}
