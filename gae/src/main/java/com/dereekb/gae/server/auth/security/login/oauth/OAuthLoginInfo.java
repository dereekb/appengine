package com.dereekb.gae.server.auth.security.login.oauth;

/**
 * Login info retrieved from a remote OAuth source.
 *
 * @author dereekb
 *
 */
public interface OAuthLoginInfo {

	/**
	 * Returns the username, generally an email address.
	 *
	 * @return
	 */
	public String getUsername();

	/**
	 * Returns the email address for the user.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEmail();

	/**
	 * Returns {@code true} if the login info is acceptable for further use.
	 *
	 * @return {@code true} if acceptable.
	 */
	public boolean isAcceptable();

}
