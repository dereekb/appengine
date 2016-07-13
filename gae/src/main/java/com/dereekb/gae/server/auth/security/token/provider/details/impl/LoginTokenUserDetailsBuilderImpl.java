package com.dereekb.gae.server.auth.security.token.provider.details.impl;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.roles.authority.GrantedAuthorityDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetailsBuilder;
import com.dereekb.gae.server.datastore.Getter;

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

	private GrantedAuthorityDecoder grantedAuthorityConverter;

	public LoginTokenUserDetailsBuilderImpl(Getter<Login> loginGetter,
	        Getter<LoginPointer> loginPointerGetter,
	        GrantedAuthorityDecoder grantedAuthorityConverter) {
		this.setLoginGetter(loginGetter);
		this.setLoginPointerGetter(loginPointerGetter);
		this.setGrantedAuthorityConverter(grantedAuthorityConverter);
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

	public GrantedAuthorityDecoder getGrantedAuthorityConverter() {
		return this.grantedAuthorityConverter;
	}

	public void setGrantedAuthorityConverter(GrantedAuthorityDecoder grantedAuthorityConverter) {
		this.grantedAuthorityConverter = grantedAuthorityConverter;
	}

	@Override
	public LoginTokenUserDetails buildDetails(LoginToken loginToken) throws IllegalArgumentException {
		return new LoginTokenUserDetailsImpl(loginToken);
	}

	// MARK: LoginTokenUserDetails
	private class LoginTokenUserDetailsImpl
	        implements LoginTokenUserDetails {

		private static final long serialVersionUID = 1L;

		private final LoginToken loginToken;
		private Set<GrantedAuthority> authorities;

		public LoginTokenUserDetailsImpl(LoginToken loginToken) throws IllegalArgumentException {
			if (loginToken == null) {
				throw new IllegalArgumentException("Token cannnot be null.");
			}

			this.loginToken = loginToken;
		}

		@Override
		public Login getLogin() {

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LoginPointer getLoginPointer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			if (this.authorities == null) {
				this.authorities = LoginTokenUserDetailsBuilderImpl.this.grantedAuthorityConverter
				        .decode(this.loginToken.getEncodedRoles());
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
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public LoginToken getLoginToken() {
			return this.loginToken;
		}

	}

}
