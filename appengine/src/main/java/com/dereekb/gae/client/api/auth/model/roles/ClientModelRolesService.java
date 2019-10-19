package com.dereekb.gae.client.api.auth.model.roles;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.web.api.auth.controller.model.roles.ModelRolesController;

/**
 * Client interface for interacting with the
 * {@link ModelRolesController}.
 *
 * @author dereekb
 *
 */
public interface ClientModelRolesService {

	/**
	 * Gets role for each of the requested models.
	 *
	 * @param request
	 *            {@link ClientModelRolesRequest}. Never
	 *            {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}, or {@code null}.
	 * @return {@link ClientModelRolesResponse}. Never {@code null}.
	 *
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	public ClientModelRolesResponseData getRolesForModels(ClientModelRolesRequest request,
	                                                      ClientRequestSecurity security)
	        throws ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException;

}
