package com.dereekb.gae.client.api.model.crud.services;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Pre-configured system service for reading objects from a remote resource.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see ReadService
 */
public interface ClientReadService<T extends UniqueModel> {

	/**
	 * Reads objects using the input request and default security.
	 *
	 * @param request
	 *            {@link ReadRequest}. Never {@code null}.
	 * @return {@link SimpleReadResponse}. Never {@code null}.
	 * 
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be read.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 * @see #read(ReadRequest, ClientRequestSecurity)
	 */
	public SimpleReadResponse<T> read(ReadRequest request)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException;

	/**
	 * Reads objects using the input request.
	 *
	 * @param request
	 *            {@link ReadRequest}. Never {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}, or {@code null}.
	 * @return {@link SimpleReadResponse}. Never {@code null}.
	 * 
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be read.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public SimpleReadResponse<T> read(ReadRequest request,
	                                  ClientRequestSecurity security)
	        throws ClientAtomicOperationException,
	            ClientRequestFailureException;

}
