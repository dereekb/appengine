package com.dereekb.gae.client.api.auth.model.context;

import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.exception.ClientAtomicOperationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.web.api.auth.controller.model.context.LoginTokenModelContextController;

/**
 * Client interface for interacting with the
 * {@link LoginTokenModelContextController}.
 *
 * @author dereekb
 * @deprecated {@link LoginTokenModelContextController} is deprecated. Use
 *             {@link ClientModelRolesService} instead.
 */
@Deprecated
public interface ClientModelRolesLoginTokenContextService {

	/**
	 * Creates a new login token using the current security.
	 *
	 * @param request
	 *            {@link ClientModelRolesLoginTokenContextRequest}. Never
	 *            {@code null}.
	 * @return {@link ClientModelRolesLoginTokenContextResponse}. Never
	 *         {@code null}.
	 *
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	@Deprecated
	public ClientModelRolesLoginTokenContextResponse getContextForModels(ClientModelRolesLoginTokenContextRequest request)
	        throws ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException;

	/**
	 * Creates a new login token using the current security, or the security
	 * provided.
	 *
	 * @param request
	 *            {@link ClientModelRolesLoginTokenContextRequest}. Never
	 *            {@code null}.
	 * @param security
	 *            {@link ClientRequestSecurity}, or {@code null}.
	 * @return {@link ClientModelRolesLoginTokenContextResponse}. Never
	 *         {@code null}.
	 *
	 * @throws ClientIllegalArgumentException
	 *             thrown if the request has an illegal argument.
	 * @throws ClientAtomicOperationException
	 *             thrown if one or more objects fail to be updated.
	 * @throws ClientRequestFailureException
	 *             thrown if the request fails for any other reason.
	 */
	@Deprecated
	public ClientModelRolesLoginTokenContextResponse getContextForModels(ClientModelRolesLoginTokenContextRequest request,
	                                                                     ClientRequestSecurity security)
	        throws ClientIllegalArgumentException,
	            ClientAtomicOperationException,
	            ClientRequestFailureException;

}
