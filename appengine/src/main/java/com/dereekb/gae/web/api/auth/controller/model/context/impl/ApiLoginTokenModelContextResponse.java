package com.dereekb.gae.web.api.auth.controller.model.context.impl;

import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesResponseData;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * API Request for login token authentication.
 *
 * @author dereekb
 */
@Deprecated
public interface ApiLoginTokenModelContextResponse {

	/**
	 *
	 * @return {@link LoginTokenPair}, never {@code null} if requested.
	 */
	public LoginTokenPair getLoginToken();

	/**
	 *
	 * @return {@link ApiModelRolesResponseData}, never {@code null} if
	 *         requested.
	 */
	public ApiModelRolesResponseData getRolesData();

}
