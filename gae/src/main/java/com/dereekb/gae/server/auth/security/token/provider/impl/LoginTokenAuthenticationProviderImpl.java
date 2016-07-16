package com.dereekb.gae.server.auth.security.token.provider.impl;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.BasicLoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetailsBuilder;

/**
 * {@link LoginTokenAuthenticationProvider} implementation.
 *
 * @author dereekb
 *
 */
public final class LoginTokenAuthenticationProviderImpl
        implements LoginTokenAuthenticationProvider {

	private LoginTokenUserDetailsBuilder loginTokenUserDetailsBuilder;

	public LoginTokenAuthenticationProviderImpl(LoginTokenUserDetailsBuilder loginTokenUserDetailsBuilder)
	        throws IllegalArgumentException {
		this.setLoginTokenUserDetailsBuilder(loginTokenUserDetailsBuilder);
	}

	public LoginTokenUserDetailsBuilder getLoginTokenUserDetailsBuilder() {
		return this.loginTokenUserDetailsBuilder;
	}

	public void setLoginTokenUserDetailsBuilder(LoginTokenUserDetailsBuilder loginTokenUserDetailsBuilder)
	        throws IllegalArgumentException {
		if (loginTokenUserDetailsBuilder == null) {
			throw new IllegalArgumentException("Cannot be null.");
		}

		this.loginTokenUserDetailsBuilder = loginTokenUserDetailsBuilder;
	}

	// MARK: Authentication Provider

	@Override
	public LoginTokenAuthentication authenticate(Authentication authentication) throws AuthenticationException {
		BasicLoginTokenAuthentication auth = (BasicLoginTokenAuthentication) authentication;
		LoginToken token = auth.getCredentials();
		WebAuthenticationDetails details = auth.getDetails();
		return this.authenticate(token, details);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return BasicLoginTokenAuthentication.class.isAssignableFrom(authentication);
	}

	// MARK: LoginTokenAuthenticationProvider
	@Override
	public LoginTokenAuthentication authenticate(LoginToken loginToken,
	                                             WebAuthenticationDetails details) {
		return this.buildAuthentication(loginToken, details);
	}

	private LoginTokenAuthentication buildAuthentication(LoginToken loginToken,
	                                                     WebAuthenticationDetails details) {
		return new LoginTokenAuthenticationImpl(loginToken, details);
	}

	private class LoginTokenAuthenticationImpl
	        implements LoginTokenAuthentication {

		private static final long serialVersionUID = 1L;

		private final LoginToken loginToken;
		private final WebAuthenticationDetails details;

		private LoginTokenUserDetails userDetails;

		public LoginTokenAuthenticationImpl(LoginToken loginToken, WebAuthenticationDetails details) {
			this.loginToken = loginToken;
			this.details = details;
		}

		// MARK: LoginTokenAuthentication
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.getPrincipal().getAuthorities();
		}

		@Override
		public LoginToken getCredentials() {
			return this.loginToken;
		}

		@Override
		public WebAuthenticationDetails getDetails() {
			return this.details;
		}

		@Override
		public boolean isAuthenticated() {
			return true; // Tokens are always authenticated.
		}

		@Override
		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			throw new IllegalArgumentException("Cannot set the token as authenticated.");
		}

		@Override
		public String getName() {
			return this.loginToken.getLogin().toString();
		}

		@Override
		public LoginTokenUserDetails getPrincipal() {
			if (this.userDetails == null) {
				this.userDetails = LoginTokenAuthenticationProviderImpl.this.loginTokenUserDetailsBuilder
				        .buildDetails(this.loginToken);
			}

			return this.userDetails;
		}

	}

}
