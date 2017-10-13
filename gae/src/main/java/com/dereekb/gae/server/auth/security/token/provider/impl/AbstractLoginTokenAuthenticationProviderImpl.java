package com.dereekb.gae.server.auth.security.token.provider.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;
import com.dereekb.gae.server.auth.security.token.provider.details.AbstractLoginTokenUserDetailsBuilder;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.preauth.PreAuthLoginTokenAuthentication;

/**
 * Abstract {@link LoginTokenAuthenticationProvider} implementation.
 *
 * @author dereekb
 *
 */
public class AbstractLoginTokenAuthenticationProviderImpl<B extends AbstractLoginTokenUserDetailsBuilder<D, T>, D extends LoginTokenUserDetails<T>, T extends LoginToken> extends AbstractLoginTokenAuthenticationProvider<B, T>
        implements LoginTokenAuthenticationProvider<T> {

	public AbstractLoginTokenAuthenticationProviderImpl(B loginTokenUserDetailsBuilder)
	        throws IllegalArgumentException {
		super(loginTokenUserDetailsBuilder);
	}

	// MARK: Authentication Provider
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		@SuppressWarnings("unchecked") 
		PreAuthLoginTokenAuthentication<T> auth = (PreAuthLoginTokenAuthentication<T>) authentication;
		DecodedLoginToken<T> decodedLoginToken = auth.getCredentials();
		WebAuthenticationDetails details = auth.getDetails();
		return this.authenticate(decodedLoginToken, details);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthLoginTokenAuthentication.class.isAssignableFrom(authentication);
	}

	// MARK: LoginTokenAuthenticationProvider
	@Override
	public LoginTokenAuthentication<T> authenticate(DecodedLoginToken<T> loginToken,
	                                                WebAuthenticationDetails details) {
		return new LoginTokenAuthenticationImpl(loginToken, details);
	}

	// MARK: Authentication
	protected class LoginTokenAuthenticationImpl extends AbstractLoginTokenAuthenticationImpl<D> {

		private static final long serialVersionUID = 1L;

		public LoginTokenAuthenticationImpl(DecodedLoginToken<T> loginToken, WebAuthenticationDetails details)
		        throws IllegalArgumentException {
			super(loginToken, details);
		}

		@Override
		protected D makePrinciple(DecodedLoginToken<T> loginToken) {
			return AbstractLoginTokenAuthenticationProviderImpl.this.builder.buildDetails(loginToken);
		}

	}

}
