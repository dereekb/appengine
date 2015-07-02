package com.dereekb.gae.model.crud.services.components;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;


/**
 * Service for reading existing objects.
 *
 * Should be a thread-safe implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type that implements the {@link UniqueModel} interface.
 */
public interface ReadService<T extends UniqueModel> {

	/**
	 * Reads objects.
	 *
	 * @param request
	 * @return {@link ReadResponse} instance.
	 * @throws AtomicOperationException
	 *             Occurs when the request specifies "atomic" and not all
	 *             objects requested can be read.
	 */
	public ReadResponse<T> read(ReadRequest<T> request) throws AtomicOperationException;

}
