package com.dereekb.gae.server.auth.security.login.oauth;

import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * OAuth access token that contains an access token string granting access to
 * remote service requests. This is equivalent to an {@link EncodedLoginToken}.
 * 
 * @author dereekb
 *
 */
public interface OAuthAccessToken {

	/**
	 * Returns the access token.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAccessToken();

	/**
	 * Returns the refresh token.
	 * 
	 * @return {@link String}. Can be {@code null}.
	 */
	public String getRefreshToken();

	/**
	 * Returns the time in seconds that this token will expire in.
	 * 
	 * @return {@link Long}. Can be {@code null}.
	 */
	public Long getExpiresIn();

}
