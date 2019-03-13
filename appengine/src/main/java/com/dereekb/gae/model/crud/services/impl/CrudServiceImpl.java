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
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

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

	public CrudServiceImpl(ReadService<T> readService) {
		this(null, readService, null, null);
	}

	public CrudServiceImpl(CreateService<T> createService,
	        ReadService<T> readService,
	        UpdateService<T> updateService,
	        DeleteService<T> deleteService) {
		this.setCreateService(createService);
		this.setReadService(readService);
		this.setUpdateService(updateService);
		this.setDeleteService(deleteService);
	}

	public void setCreateService(CreateService<T> createService) {
		if (createService == null) {
			createService = new CreateService<T>() {

				@Override
				public CreateResponse<T> create(CreateRequest<T> request) throws AtomicOperationException {
					throw new UnsupportedOperationException();
				}

			};
		}

		this.createService = createService;
	}

	public void setReadService(ReadService<T> readService) {
		if (readService == null) {
			readService = new ReadService<T>() {

				@Override
				public ReadResponse<ModelKey> exists(ReadRequest request) throws AtomicOperationException {
					throw new UnsupportedOperationException();
				}

				@Override
				public ReadResponse<T> read(ReadRequest request) throws AtomicOperationException {
					throw new UnsupportedOperationException();
				}

			};
		}

		this.readService = readService;
	}

	public void setUpdateService(UpdateService<T> updateService) {
		if (updateService == null) {
			updateService = new UpdateService<T>() {

				@Override
				public UpdateResponse<T> update(UpdateRequest<T> request) throws AtomicOperationException {
					throw new UnsupportedOperationException();
				}

			};
		}

		this.updateService = updateService;
	}

	public void setDeleteService(DeleteService<T> deleteService) {
		if (deleteService == null) {
			deleteService = new DeleteService<T>() {

				@Override
				public DeleteResponse<T> delete(DeleteRequest request) throws AtomicOperationException {
					throw new UnsupportedOperationException();
				}

			};
		}

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
	public ReadResponse<ModelKey> exists(ReadRequest request) throws AtomicOperationException {
		return this.readService.exists(request);
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
