package com.dereekb.gae.server.auth.security.token.provider;

import com.dereekb.gae.server.auth.security.login.LoginAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;

/**
 * {@link LoginAuthentication} extension.
 *
 * @author dereekb
 *
 */
public interface LoginTokenAuthentication
        extends LoginAuthentication, BasicLoginTokenAuthentication {

	@Override
	public LoginTokenUserDetails getPrincipal();

}
