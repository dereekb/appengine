package com.dereekb.gae.client.api.auth.model.roles;

/**
 * Client response for a {@link ClientModelRolesRequest}.
 *
 * @author dereekb
 *
 */
public interface ClientModelRolesResponse {

	/**
	 *
	 * @return {@link ClientModelRolesResponseData}. Never {@code null}.
	 */
	public ClientModelRolesResponseData getRolesData();

}
