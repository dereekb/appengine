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

	public boolean isAnonymous();

}
