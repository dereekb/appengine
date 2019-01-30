package com.dereekb.gae.server.auth.security.login.oauth;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;

/**
 * Login info retrieved from a remote OAuth source.
 *
 * @author dereekb
 *
 */
public interface OAuthLoginInfo {

	/**
	 * Returns the login type used.
	 *
	 * @return {@link LoginPointerType}. Never {@code null}.
	 */
	public LoginPointerType getLoginType();

	/**
	 * Returns the id, generally an email address.
	 *
	 * @return Id. Never {@code null}.
	 */
	public String getId();

	/**
	 * Returns the name of the user.
	 *
	 * @return {@link String}. May be {@code null}.
	 */
	public String getName();

	/**
	 * Returns the email address for the user.
	 *
	 * @return {@link String}. May be {@code null}.
	 */
	public String getEmail();

	/**
	 * Returns {@code true} if the login info is acceptable for further use.
	 *
	 * @return {@code true} if acceptable.
	 */
	public boolean isAcceptable();

}
