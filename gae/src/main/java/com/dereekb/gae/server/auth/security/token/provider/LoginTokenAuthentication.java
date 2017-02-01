package com.dereekb.gae.server.auth.security.token.provider;

import com.dereekb.gae.server.auth.security.login.LoginAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.preauth.PreAuthLoginTokenAuthentication;

/**
 * {@link LoginAuthentication} extension that also implements
 * {@link PreAuthLoginTokenAuthentication}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenAuthentication
        extends LoginAuthentication, PreAuthLoginTokenAuthentication {

	/**
	 * @return {@link LoginTokenUserDetails}. Never {@code null}.
	 */
	@Override
	public LoginTokenUserDetails getPrincipal();

}
