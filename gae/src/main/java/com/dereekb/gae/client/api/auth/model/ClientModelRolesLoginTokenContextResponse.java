package com.dereekb.gae.client.api.auth.model;

import com.dereekb.gae.web.api.auth.controller.model.impl.ApiModelRolesResponseData;
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

	public ApiModelRolesResponseData getResponseData();

}
