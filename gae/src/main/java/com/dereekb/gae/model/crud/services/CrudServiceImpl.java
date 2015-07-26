package com.dereekb.gae.model.crud.services;

import com.dereekb.gae.model.crud.function.CreateFunction;
import com.dereekb.gae.model.crud.function.DeleteFunction;
import com.dereekb.gae.model.crud.function.ReadFunction;
import com.dereekb.gae.model.crud.function.UpdateFunction;
import com.dereekb.gae.model.crud.services.components.impl.CreateServiceImpl;
import com.dereekb.gae.model.crud.services.components.impl.DeleteServiceImpl;
import com.dereekb.gae.model.crud.services.components.impl.ReadServiceImpl;
import com.dereekb.gae.model.crud.services.components.impl.UpdateServiceImpl;
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
import com.dereekb.gae.utilities.factory.Factory;

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

	protected final CreateServiceImpl<T> createService;
	protected final ReadServiceImpl<T> readService;
	protected final UpdateServiceImpl<T> updateService;
	protected final DeleteServiceImpl<T> deleteService;

	public CrudServiceImpl(Factory<CreateFunction<T>> createFactory,
	        Factory<ReadFunction<T>> readFactory,
	        Factory<UpdateFunction<T>> updateFactory,
	        Factory<DeleteFunction<T>> deleteFactory) {

		this.createService = new CreateServiceImpl<T>(createFactory);
		this.readService = new ReadServiceImpl<T>(readFactory);
		this.updateService = new UpdateServiceImpl<T>(this.readService, updateFactory);
		this.deleteService = new DeleteServiceImpl<T>(this.readService, deleteFactory);
	}

	public CrudServiceImpl(CreateServiceImpl<T> createService,
	        ReadServiceImpl<T> readService,
	        UpdateServiceImpl<T> updateService,
	        DeleteServiceImpl<T> deleteService) {
		this.createService = createService;
		this.readService = readService;
		this.updateService = updateService;
		this.deleteService = deleteService;
	}

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
	public DeleteResponse<T> delete(DeleteRequest<T> request) throws AtomicOperationException {
		return this.deleteService.delete(request);
	}

}
