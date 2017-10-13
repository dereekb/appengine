package com.dereekb.gae.server.auth.security.token.provider;

import com.dereekb.gae.server.auth.security.login.LoginAuthentication;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.preauth.PreAuthLoginTokenAuthentication;

/**
 * {@link LoginAuthentication} extension that also implements
 * {@link PreAuthLoginTokenAuthentication}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenAuthentication<T extends LoginToken>
        extends LoginAuthentication, PreAuthLoginTokenAuthentication<T> {

	/**
	 * @return {@link LoginTokenUserDetails}. Never {@code null}.
	 */
	@Override
	public LoginTokenUserDetails<T> getPrincipal();

}
