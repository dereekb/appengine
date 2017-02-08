package com.dereekb.gae.client.api.model.crud.components;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.model.crud.services.components.UpdateService;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.response.UpdateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Service for updating models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see UpdateService
 */
public interface ClientUpdateService<T extends UniqueModel> {

	/**
	 * Updates objects using the input request.
	 *
	 * @param request
	 *            {@link UpdateRequest}. Never {@code null}.
	 * @return {@link UpdateResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public UpdateResponse<T> update(UpdateRequest<T> request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException;

}
