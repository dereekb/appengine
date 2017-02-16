package com.dereekb.gae.server.auth.security.token.provider.details;

import com.dereekb.gae.server.auth.security.login.LoginUserDetails;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link LoginUserDetails} extension that includes a {@link LoginToken}.
 *
 * @author dereekb
 *
 */
public interface LoginTokenUserDetails
        extends LoginUserDetails {

	/**
	 * @return {@link DecodedLoginToken}. Never {@code null}.
	 */
	public DecodedLoginToken getLoginToken();

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
