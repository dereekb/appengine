package com.dereekb.gae.client.api.auth.model.context;

import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesResponse;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Client response for a {@link ClientModelRolesLoginTokenContextRequest}.
 *
 * @author dereekb
 * @deprecated
 */
@Deprecated
public interface ClientModelRolesLoginTokenContextResponse
        extends ClientModelRolesResponse {

	/**
	 * Returns the created token pair.
	 *
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 */
	public LoginTokenPair getLoginTokenPair();

}
