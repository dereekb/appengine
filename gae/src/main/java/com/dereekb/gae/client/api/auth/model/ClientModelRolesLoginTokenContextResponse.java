package com.dereekb.gae.client.api.auth.model;

import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Client response for a {@link ClientModelRolesLoginTokenContextRequest}.
 *
 * @author dereekb
 *
 */
public interface ClientModelRolesLoginTokenContextResponse {

	/**
	 * Returns the created token pair.
	 *
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 */
	public LoginTokenPair getLoginTokenPair();

	/**
	 *
	 * @return {@link ClientModelRolesResponseData}. Never {@code null}.
	 */
	public ClientModelRolesResponseData getRolesData();

}
