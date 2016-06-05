package com.dereekb.gae.model.crud.services.impl;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link CrudService} implementation that uses implementations of the different
 * services to fulfill the interface.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class CrudServiceImpl<T extends UniqueModel>
        implements CrudService<T> {

	private CreateService<T> createService;
	private ReadService<T> readService;
	private UpdateService<T> updateService;
	private DeleteService<T> deleteService;

	public CrudServiceImpl(CreateService<T> createService,
	        ReadService<T> readService,
	        UpdateService<T> updateService,
	        DeleteService<T> deleteService) {
		this.createService = createService;
		this.readService = readService;
		this.updateService = updateService;
		this.deleteService = deleteService;
	}

	public CreateService<T> getCreateService() {
		return this.createService;
	}

	public void setCreateService(CreateService<T> createService) {
		this.createService = createService;
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		this.readService = readService;
	}

	public UpdateService<T> getUpdateService() {
		return this.updateService;
	}

	public void setUpdateService(UpdateService<T> updateService) {
		this.updateService = updateService;
	}

	public DeleteService<T> getDeleteService() {
		return this.deleteService;
	}

	public void setDeleteService(DeleteService<T> deleteService) {
		this.deleteService = deleteService;
	}

	// MARK: CrudService
	@Override
	public CreateResponse<T> create(CreateRequest<T> request) throws AtomicOperationException {
		return this.createService.create(request);
	}

	@Override
	public ReadResponse<T> read(ReadRequest request) throws AtomicOperationException {
		return this.readService.read(request);
	}

	@Override
	public UpdateResponse<T> update(UpdateRequest<T> request) throws AtomicOperationException {
		return this.updateService.update(request);
	}

	@Override
	public DeleteResponse<T> delete(DeleteRequest request) throws AtomicOperationException {
		return this.deleteService.delete(request);
	}

}