package com.dereekb.gae.web.api.auth.controller.model;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiLoginTokenModelContextRequest;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiModelRolesRequest;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiModelRolesResponseData;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link LoginTokenModelContextController} delegate.
 *
 * @author dereekb
 *
 */
public interface LoginTokenModelContextControllerDelegate {

	/**
	 * Performs a login with the request.
	 *
	 * @param request
	 *            {@link ApiLoginTokenModelContextRequest}. Never {@code null}.
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * @throws UnsupportedOperationException
	 *             thrown if a new LoginToken is requested as part of the
	 *             response, but is not available.
	 * @throws NoSecurityContextException
	 *             thrown if
	 * @throws AtomicOperationException
	 */
	public ApiLoginTokenModelContextResponse loginWithContext(ApiLoginTokenModelContextRequest request)
	        throws NoSecurityContextException,
	            UnsupportedOperationException,
	            AtomicOperationException;

	/**
	 *
	 * @param request
	 *            {@link ApiModelRolesRequest}. Never {@code null}.
	 * @return {@link ApiModelRolesResponse}. Never {@code null}.
	 *
	 * @throws NoSecurityContextException
	 * @throws AtomicOperationException
	 */
	public ApiModelRolesResponseData readRoles(ApiModelRolesRequest request)
	        throws NoSecurityContextException,
	            AtomicOperationException;

}
