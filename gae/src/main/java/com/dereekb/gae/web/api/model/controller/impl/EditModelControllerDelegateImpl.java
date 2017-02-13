package com.dereekb.gae.web.api.model.controller.impl;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.model.controller.EditModelControllerDelegate;

/**
 * {@link EditModelControllerDelegate} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class EditModelControllerDelegateImpl<T extends UniqueModel>
        implements EditModelControllerDelegate<T> {

	private CrudService<T> service;

	public EditModelControllerDelegateImpl(CrudService<T> service) {
		this.service = service;
	}

	public CrudService<T> getService() {
		return this.service;
	}

	public void setService(CrudService<T> service) {
		this.service = service;
	}

	// EditModelControllerDelegate
	@Override
	public CreateResponse<T> create(CreateRequest<T> request)
	        throws UnsupportedOperationException,
	            AtomicOperationException {
		return this.service.create(request);
	}

	@Override
	public UpdateResponse<T> update(UpdateRequest<T> request)
	        throws UnsupportedOperationException,
	            AtomicOperationException {
		return this.service.update(request);
	}

	@Override
	public DeleteResponse<T> delete(DeleteRequest request) throws AtomicOperationException {
		return this.service.delete(request);
	}

}
