package com.dereekb.gae.web.api.auth.controller.model;

import com.dereekb.gae.web.api.auth.controller.model.impl.ApiLoginTokenModelContextRequest;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
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
	 */
	public LoginTokenPair loginWithContext(ApiLoginTokenModelContextRequest request);

}
