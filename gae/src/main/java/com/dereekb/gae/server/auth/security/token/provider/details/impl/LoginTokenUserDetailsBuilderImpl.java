package com.dereekb.gae.server.auth.security.token.provider.details.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.roles.authority.GrantedAuthorityDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetailsBuilder;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LoginTokenUserDetailsBuilder} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenUserDetailsBuilderImpl
        implements LoginTokenUserDetailsBuilder {

	private Getter<Login> loginGetter;
	private Getter<LoginPointer> loginPointerGetter;

	private GrantedAuthorityDecoder grantedAuthorityDecoder;

	private List<GrantedAuthority> tokenAuthorities;

	public LoginTokenUserDetailsBuilderImpl(Getter<Login> loginGetter,
	        Getter<LoginPointer> loginPointerGetter,
	        GrantedAuthorityDecoder grantedAuthorityDecoder) {
		this(loginGetter, loginPointerGetter, grantedAuthorityDecoder, null);
	}

	public LoginTokenUserDetailsBuilderImpl(Getter<Login> loginGetter,
	        Getter<LoginPointer> loginPointerGetter,
	        GrantedAuthorityDecoder grantedAuthorityDecoder,
	        List<GrantedAuthority> tokenAuthorities) {
		this.setLoginGetter(loginGetter);
		this.setLoginPointerGetter(loginPointerGetter);
		this.setGrantedAuthorityDecoder(grantedAuthorityDecoder);
		this.setTokenAuthorities(tokenAuthorities);
	}

	public Getter<Login> getLoginGetter() {
		return this.loginGetter;
	}

	public void setLoginGetter(Getter<Login> loginGetter) {
		this.loginGetter = loginGetter;
	}

	public Getter<LoginPointer> getLoginPointerGetter() {
		return this.loginPointerGetter;
	}

	public void setLoginPointerGetter(Getter<LoginPointer> loginPointerGetter) {
		this.loginPointerGetter = loginPointerGetter;
	}

	public GrantedAuthorityDecoder getGrantedAuthorityDecoder() {
		return this.grantedAuthorityDecoder;
	}

	public void setGrantedAuthorityDecoder(GrantedAuthorityDecoder grantedAuthorityDecoder) {
		this.grantedAuthorityDecoder = grantedAuthorityDecoder;
	}

	public List<GrantedAuthority> getTokenAuthorities() {
		return this.tokenAuthorities;
	}

	public void setTokenAuthorities(List<GrantedAuthority> tokenAuthorities) {
		if (tokenAuthorities == null) {
			tokenAuthorities = new ArrayList<GrantedAuthority>();
		}

		this.tokenAuthorities = tokenAuthorities;
	}

	// MARK: LoginTokenUserDetailsBuilder
	@Override
	public LoginTokenUserDetails buildDetails(LoginToken loginToken) throws IllegalArgumentException {
		return new LoginTokenUserDetailsImpl(loginToken);
	}

	// MARK: LoginTokenUserDetails
	private class LoginTokenUserDetailsImpl
	        implements LoginTokenUserDetails {

		private static final long serialVersionUID = 1L;

		private final LoginToken loginToken;

		private boolean loginLoaded = false;
		private Login login = null;
		private LoginPointer loginPointer = null;

		private Set<GrantedAuthority> authorities;

		public LoginTokenUserDetailsImpl(LoginToken loginToken) throws IllegalArgumentException {
			if (loginToken == null) {
				throw new IllegalArgumentException("Token cannnot be null.");
			}

			this.loginToken = loginToken;
		}

		@Override
		public Login getLogin() {
			if (this.loginLoaded == false) {
				Long id = this.loginToken.getLogin();
				ModelKey key = new ModelKey(id);
				this.login = LoginTokenUserDetailsBuilderImpl.this.loginGetter.get(key);
			}

			return this.login;
		}

		@Override
		public LoginPointer getLoginPointer() {
			if (this.loginPointer == null) {
				String id = this.loginToken.getLoginPointer();
				ModelKey key = new ModelKey(id);
				this.loginPointer = LoginTokenUserDetailsBuilderImpl.this.loginPointerGetter.get(key);
			}

			return this.loginPointer;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			if (this.authorities == null) {
				Long encodedRoles = this.loginToken.getRoles();

				if (encodedRoles == null) {
					this.authorities = LoginTokenUserDetailsBuilderImpl.this.grantedAuthorityDecoder
					        .decodeRoles(encodedRoles);
				} else {
					this.authorities = new HashSet<GrantedAuthority>();
				}

				this.authorities.addAll(LoginTokenUserDetailsBuilderImpl.this.tokenAuthorities);
			}

			return this.authorities;
		}

		@Override
		public String getPassword() {
			return null; // Password not available
		}

		@Override
		public String getUsername() {
			return this.loginToken.getLoginPointer();
		}

		@Override
		public boolean isAccountNonExpired() {
			return false; // Accounts do not expire.
		}

		@Override
		public boolean isAccountNonLocked() {
			return false; // Accounts are never locked.
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return this.loginToken.hasExpired();
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public LoginToken getLoginToken() {
			return this.loginToken;
		}

	}

}
