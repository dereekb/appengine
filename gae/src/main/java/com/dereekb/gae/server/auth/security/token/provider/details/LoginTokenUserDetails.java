package com.dereekb.gae.server.auth.security.token.provider.details;

import com.dereekb.gae.server.auth.security.login.LoginUserDetails;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link LoginUserDetails} extension that includes a {@link LoginToken}.
 *
 * @author dereekb
 *
 * @param <T>
 *            token type
 */
public interface LoginTokenUserDetails<T extends LoginToken>
        extends LoginUserDetails {

	/**
	 * Returns the login token from {@link #getDecodedLoginToken()}. Never
	 * {@code null}.
	 */
	public T getLoginToken();

	/**
	 * Returns the {@link LoginTokenModelContextSet} for the token.
	 *
	 * @return {@link LoginTokenModelContextSet}. Never {@code null}.
	 */
	public LoginTokenModelContextSet getLoginTokenModelContextSet();

	/**
	 * @return {@link DecodedLoginToken}. Never {@code null}.
	 */
	public DecodedLoginToken<T> getDecodedLoginToken();

	/**
	 * Whether or not the user is an administrator.
	 *
	 * @return {@code true} if the user is an administrator.
	 */
	public boolean isAdministrator();

	/**
	 * Whether or not the user is logged in anonymously.
	 *
	 * @return {@code true} if the user is anonymous.
	 */
	public boolean isAnonymous();

	/**
	 * Returns a user type corresponding to this token.
	 *
	 * @return {@link LoginTokenUserType}. Never {@code null}.
	 */
	public LoginTokenUserType getUserType();

}
