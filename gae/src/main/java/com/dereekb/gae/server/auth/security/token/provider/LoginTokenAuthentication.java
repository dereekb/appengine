package com.dereekb.gae.server.auth.security.token.provider;

import com.dereekb.gae.server.auth.security.login.LoginAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;

/**
 * {@link LoginAuthentication} extension that also implements
 * {@link BasicLoginTokenAuthentication}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenAuthentication
        extends LoginAuthentication, BasicLoginTokenAuthentication {

	/**
	 * @return {@link LoginTokenUserDetails}. Never {@code null}.
	 */
	@Override
	public LoginTokenUserDetails getPrincipal();

}
