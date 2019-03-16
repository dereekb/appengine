package com.dereekb.gae.model.crud.services;

import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Thread-safe service that can provide Create, Update, and Delete
 * functionality.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type
 */
public interface EditService<T extends UniqueModel>
        extends CreateService<T>, UpdateService<T>, DeleteService<T> {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws UnsupportedOperationException
	 *             if this request type is unsupported by this service.
	 */
	@Override
	public CreateResponse<T> create(CreateRequest<T> request)
	        throws UnsupportedOperationException,
	            AtomicOperationException;

	/**
	 * {@inheritDoc}
	 * 
	 * @throws UnsupportedOperationException
	 *             if this request type is unsupported by this service.
	 */
	@Override
	public UpdateResponse<T> update(UpdateRequest<T> request)
	        throws UnsupportedOperationException,
	            AtomicOperationException;

	/**
	 * {@inheritDoc}
	 * 
	 * @throws UnsupportedOperationException
	 *             if this request type is unsupported by this service.
	 */
	@Override
	public DeleteResponse<T> delete(DeleteRequest request) throws AtomicOperationException;

}
