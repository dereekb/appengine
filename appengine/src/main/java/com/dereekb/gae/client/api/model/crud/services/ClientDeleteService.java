package com.dereekb.gae.client.api.model.crud.services;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.request.ClientDeleteRequest;
import com.dereekb.gae.client.api.model.crud.response.ClientDeleteResponse;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.response.SimpleDeleteResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Pre-configured system service for deleting models.
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
	 * Deletes objects using the input request and default security.
	 *
	 * @param request
	 *            {@link DeleteRequest}. Never {@code null}.
	 * @return {@link SimpleDeleteResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 * @see #delete(ClientDeleteRequest)
	 */
	public ClientDeleteResponse<T> delete(DeleteRequest request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException;

	/**
	 * Deletes objects using the input request and default security.
	 *
	 * @param request
	 *            {@link DeleteRequest}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}, or {@code null}.
	 * @return {@link SimpleDeleteResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 * @see #delete(ClientDeleteRequest)
	 */
	public ClientDeleteResponse<T> delete(DeleteRequest request,
	                                      ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException;

	/**
	 * Deletes objects using the input request and default security.
	 *
	 * @param request
	 *            {@link ClientDeleteRequest}. Never {@code null}.
	 * @return {@link SimpleDeleteResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 * @see #delete(ClientDeleteRequest, ClientRequestSecurity)
	 */
	public ClientDeleteResponse<T> delete(ClientDeleteRequest request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException;

	/**
	 * Deletes objects using the input request.
	 *
	 * @param request
	 *            {@link ClientDeleteRequest}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}, or {@code null}.
	 * @return {@link SimpleDeleteResponse}. Never {@code null}.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientDeleteResponse<T> delete(ClientDeleteRequest request,
	                                      ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException;

}
