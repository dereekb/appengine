package com.dereekb.gae.server.auth.security.system;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link SystemLoginTokenService} response.
 *
 * @author dereekb
 *
 */
public interface SystemLoginTokenResponse {

	/**
	 * Returns the generated login token.
	 *
	 * @return {@link LoginToken}. Never {@code null}.
	 */
	public LoginToken getLoginToken();

	/**
	 * Returns the encoded login token.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEncodedLoginToken();

}
