package com.dereekb.gae.server.auth.security.system;

/**
 * {@link SystemLoginTokenService} request.
 *
 * @author dereekb
 *
 */
public interface SystemLoginTokenRequest {

	/**
	 * Returns the app's key.
	 *
	 * @return {@link String}, never {@code null}.
	 */
	public String getAppId();

	/**
	 * Returns the amount of time the token should last for in milliseconds.
	 *
	 * @return {@link Long}, or {@code null}.
	 */
	public Long getExpiresIn();

	/**
	 * Returns the custom specified roles.
	 *
	 * @return {@link Long}, or {@code null}.
	 */
	public Long getRoles();

}
