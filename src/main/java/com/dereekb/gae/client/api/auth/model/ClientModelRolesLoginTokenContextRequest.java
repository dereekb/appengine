package com.dereekb.gae.client.api.auth.model;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiLoginTokenModelContextRequest;

/**
 * Client request to create a new {@link LoginToken}.
 *
 * @author dereekb
 * @see ApiLoginTokenModelContextRequest
 */
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
