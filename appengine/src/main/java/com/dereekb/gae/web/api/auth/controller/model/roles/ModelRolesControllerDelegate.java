package com.dereekb.gae.web.api.auth.controller.model.roles;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesRequest;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesResponseData;

/**
 * {@link ModelRolesController} delegate.
 *
 * @author dereekb
 *
 */
public interface ModelRolesControllerDelegate {

	/**
	 *
	 * @param request
	 *            {@link ApiModelRolesRequest}. Never {@code null}.
	 * @return {@link ApiModelRolesResponseData}. Never {@code null}.
	 *
	 * @throws NoSecurityContextException
	 * @throws AtomicOperationException
	 */
	public ApiModelRolesResponseData readRoles(ApiModelRolesRequest request)
	        throws NoSecurityContextException,
	            AtomicOperationException;

}
