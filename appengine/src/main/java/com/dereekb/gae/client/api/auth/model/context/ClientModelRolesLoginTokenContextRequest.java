package com.dereekb.gae.client.api.auth.model.context;

import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesRequest;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.web.api.auth.controller.model.context.impl.ApiLoginTokenModelContextRequest;

/**
 * Client request to create a new {@link LoginToken}.
 *
 * @author dereekb
 * @see ApiLoginTokenModelContextRequest
 * @deprecated
 */
@Deprecated
public interface ClientModelRolesLoginTokenContextRequest
        extends ClientModelRolesRequest {

	/**
	 * Returns the optional expiration time.
	 *
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getExpirationTime();

	/**
	 * Whether or not the roles should be returned in the request.
	 *
	 * @return {@code true} if a new token context should be made.
	 */
	public Boolean getMakeContext();

	/**
	 * Whether or not the roles should be returned in the request.
	 *
	 * @return {@code true} if roles info should be included.
	 */
	public Boolean getIncludeRoles();

}
