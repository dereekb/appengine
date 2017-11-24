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
	 * @throws NoSecurityContextException
	 * @throws AtomicOperationException
	 */
	public LoginTokenPair loginWithContext(ApiLoginTokenModelContextRequest request)
	        throws NoSecurityContextException,
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
