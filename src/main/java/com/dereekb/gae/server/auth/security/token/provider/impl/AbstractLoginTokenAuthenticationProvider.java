package com.dereekb.gae.server.auth.security.token.provider.impl;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;

/**
 * 
 * @author dereekb
 *
 * @param <B>
 *            builder type
 * @param <T>
 *            token type
 */
public abstract class AbstractLoginTokenAuthenticationProvider<B, T extends LoginToken>
        implements AuthenticationProvider {

	private static final String UNREGISTERED_DEFAULT_NAME = "Unregistered";

	protected B builder;

	public AbstractLoginTokenAuthenticationProvider(B builder) {
		this.setBuilder(builder);
	}

	public B getBuilder() {
		return this.builder;
	}

	public void setBuilder(B builder) throws IllegalArgumentException {
		if (builder == null) {
			throw new IllegalArgumentException("Builder cannot be null.");
		}

		this.builder = builder;
	}

	// MARK: Abstract
	protected abstract class AbstractLoginTokenAuthenticationImpl<U extends LoginTokenUserDetails<T>>
	        implements LoginTokenAuthentication<T> {

		private static final long serialVersionUID = 1L;

		private final DecodedLoginToken<T> decodedLoginToken;
		private final WebAuthenticationDetails details;

		private U userDetails;

		public AbstractLoginTokenAuthenticationImpl(DecodedLoginToken<T> decodedLoginToken, WebAuthenticationDetails details)
		        throws IllegalArgumentException {
			if (decodedLoginToken == null) {
				throw new IllegalArgumentException("LoginToken cannot be null.");
			}

			this.decodedLoginToken = decodedLoginToken;
			this.details = details;
		}

		// MARK: LoginTokenAuthentication
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.getPrincipal().getAuthorities();
		}

		@Override
		public DecodedLoginToken<T> getCredentials() {
			return this.decodedLoginToken;
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
			Long loginId = this.decodedLoginToken.getLoginToken().getLoginId();
			String name = (loginId != null) ? loginId.toString() : UNREGISTERED_DEFAULT_NAME;
			return name;
		}

		@Override
		public U getPrincipal() {
			if (this.userDetails == null) {
				this.userDetails = this.makePrinciple(this.decodedLoginToken);
			}

			return this.userDetails;
		}

		protected abstract U makePrinciple(DecodedLoginToken<T> decodedLoginToken);

	}
}
