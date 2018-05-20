package com.dereekb.gae.server.auth.security.system;

import com.dereekb.gae.server.auth.security.system.exception.SystemLoginTokenServiceException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Service used for generating system login tokens for the specified app.
 *
 * @author dereekb
 *
 */
public interface SystemLoginTokenService {

	/**
	 * Generates a new {@link LoginTokenPair} for the requested system.
	 *
	 * @param request
	 *            {@link SystemLoginTokenRequest}. Never {@code null}.
	 * @return {@link SystemLoginTokenResponse}. Never {@code null}.
	 *
	 * @throws SystemLoginTokenServiceException
	 */
	public SystemLoginTokenResponse makeSystemToken(SystemLoginTokenRequest request)
	        throws SystemLoginTokenServiceException;

}
