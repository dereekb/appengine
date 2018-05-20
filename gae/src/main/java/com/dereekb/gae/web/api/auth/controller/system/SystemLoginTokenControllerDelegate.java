package com.dereekb.gae.web.api.auth.controller.system;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenRequest;
import com.dereekb.gae.server.auth.security.system.exception.SystemLoginTokenServiceException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link SystemLoginTokenController} delegate.
 *
 * @author dereekb
 *
 */
public interface SystemLoginTokenControllerDelegate {

	/**
	 * Generates a new system login token pair.
	 *
	 * @param request
	 * @return {@link LoginTokenPair}. Never {@code null}.
	 * @throws SystemLoginTokenServiceException
	 */
	public LoginTokenPair makeSystemToken(SystemLoginTokenRequest request) throws SystemLoginTokenServiceException;

}
