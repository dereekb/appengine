package com.dereekb.gae.server.auth.security.token.provider.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetailsBuilder;
import com.dereekb.gae.server.auth.security.token.provider.preauth.PreAuthLoginTokenAuthentication;

/**
 * {@link LoginTokenAuthenticationProvider} implementation.
 *
 * @author dereekb
 *
 */
public final class LoginTokenAuthenticationProviderImpl extends AbstractLoginTokenAuthenticationProvider<LoginTokenUserDetailsBuilder, LoginToken>
        implements LoginTokenAuthenticationProvider {

	public LoginTokenAuthenticationProviderImpl(LoginTokenUserDetailsBuilder loginTokenUserDetailsBuilder)
	        throws IllegalArgumentException {
		super(loginTokenUserDetailsBuilder);
	}

	// MARK: Authentication Provider
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		PreAuthLoginTokenAuthentication auth = (PreAuthLoginTokenAuthentication) authentication;
		LoginToken token = auth.getCredentials();
		WebAuthenticationDetails details = auth.getDetails();
		return this.authenticate(token, details);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthLoginTokenAuthentication.class.isAssignableFrom(authentication);
	}

	// MARK: LoginTokenAuthenticationProvider
	@Override
	public LoginTokenAuthentication authenticate(LoginToken loginToken,
	                                             WebAuthenticationDetails details) {
		return new LoginTokenAuthenticationImpl(loginToken, details);
	}

	// MARK: Authentication
	protected class LoginTokenAuthenticationImpl extends AbstractLoginTokenAuthenticationImpl<LoginTokenUserDetails> {

		private static final long serialVersionUID = 1L;

		public LoginTokenAuthenticationImpl(LoginToken loginToken, WebAuthenticationDetails details)
		        throws IllegalArgumentException {
			super(loginToken, details);
		}

		@Override
		protected LoginTokenUserDetails makePrinciple(LoginToken loginToken) {
			return LoginTokenAuthenticationProviderImpl.this.builder.buildDetails(loginToken);
		}

	}

}
