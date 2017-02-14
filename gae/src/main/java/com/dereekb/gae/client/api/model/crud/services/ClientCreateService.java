package com.dereekb.gae.client.api.model.crud.services;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.model.exception.ClientKeyedInvalidAttributeException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Pre-configured system service for creating models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see CreateService
 */
public interface ClientCreateService<T extends UniqueModel> {

	/**
	 * Creates new objects using the input request and default security.
	 *
	 * @param request
	 *            {@link CreateRequest}. Never {@code null}.
	 * @return {@link CreateResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientKeyedInvalidAttributeException
	 *             if one or more templates are invalid.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public CreateResponse<T> create(CreateRequest<T> request)
	        throws ClientAtomicOperationException,
	            ClientKeyedInvalidAttributeException,
	            ClientRequestFailureException;

	/**
	 * Creates new objects using the input request.
	 *
	 * @param request
	 *            {@link CreateRequest}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}, or {@code null}.
	 * @return {@link CreateResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientKeyedInvalidAttributeException
	 *             if one or more templates are invalid.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public CreateResponse<T> create(CreateRequest<T> request,
	                                ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientKeyedInvalidAttributeException,
	            ClientRequestFailureException;

}
