package com.dereekb.gae.server.auth.security.authentication;


/**
 * Factory for generating new {@link LoginAuthenticationImpl} instances.
 *
 * @author dereekb
 */
public final class LoginAuthenticationFactoryImpl
        implements LoginAuthenticationFactory {

	private final LoginAuthenticationAuthorityDelegate authorityDelegate;

	public LoginAuthenticationFactoryImpl(LoginAuthenticationAuthorityDelegate authorityDelegate) {
		this.authorityDelegate = authorityDelegate;
	}

	public LoginAuthenticationAuthorityDelegate getAuthorityDelegate() {
		return this.authorityDelegate;
	}

	@Override
	public LoginAuthentication makeAuthentication(LoginTuple loginTuple) {

		return null;
	}


}
