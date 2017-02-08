package com.dereekb.gae.client.api.model.crud.components;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.request.ClientDeleteRequest;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Service for deleting models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see DeleteService
 */
public interface ClientDeleteService<T extends UniqueModel> {

	/**
	 * Deletes objects using the input request.
	 *
	 * @param request
	 *            {@link ClientDeleteRequest}. Never {@code null}.
	 * @return {@link DeleteResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public DeleteResponse<T> delete(ClientDeleteRequest request) throws AtomicOperationException;

	/**
	 * Deletes objects using the input request.
	 *
	 * @param request
	 *            {@link DeleteRequest}. Never {@code null}.
	 * @return {@link DeleteResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public DeleteResponse<T> delete(DeleteRequest request) throws AtomicOperationException;

}
