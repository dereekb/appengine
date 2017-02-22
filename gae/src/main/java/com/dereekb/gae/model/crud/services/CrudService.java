package com.dereekb.gae.model.crud.services;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Thread-safe service that can provide Create, Read, Update, and Delete
 * functionality.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type
 */
public interface CrudService<T extends UniqueModel>
        extends ReadService<T>, EditService<T> {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws UnsupportedOperationException
	 *             if this request type is unsupported by this service.
	 */
	@Override
	public ReadResponse<T> read(ReadRequest request) throws UnsupportedOperationException, AtomicOperationException;

}
