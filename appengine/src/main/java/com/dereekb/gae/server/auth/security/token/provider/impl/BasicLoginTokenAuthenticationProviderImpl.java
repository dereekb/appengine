package com.dereekb.gae.server.auth.security.token.provider.impl;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetailsBuilder;

/**
 * {@link LoginTokenAuthenticationProvider} implementation.
 *
 * @author dereekb
 *
 */
public class BasicLoginTokenAuthenticationProviderImpl<T extends LoginToken> extends LoginTokenAuthenticationProviderImpl<LoginTokenUserDetailsBuilder<T>, LoginTokenUserDetails<T>, T>
        implements LoginTokenAuthenticationProvider<T> {

	public BasicLoginTokenAuthenticationProviderImpl(LoginTokenUserDetailsBuilder<T> loginTokenUserDetailsBuilder)
	        throws IllegalArgumentException {
		super(loginTokenUserDetailsBuilder);
	}

}
