package com.dereekb.gae.server.auth.security.login.oauth;

/**
 * Authorization code.
 * 
 * @author dereekb
 *
 */
public interface OAuthAuthCode {

	/**
	 * Returns the auth code string.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAuthCode();

	/**
	 * Returns the optional code type.
	 * 
	 * @return {@link String}. May be {@code null}.
	 */
	public String getCodeType();

}
