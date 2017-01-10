package com.dereekb.gae.server.auth.security.token.provider.details;

import com.dereekb.gae.server.auth.security.login.LoginUserDetails;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link LoginUserDetails} extension that includes a {@link LoginToken}.
 *
 * @author dereekb
 *
 */
public interface LoginTokenUserDetails
        extends LoginUserDetails {

	public LoginToken getLoginToken();
	
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

}
