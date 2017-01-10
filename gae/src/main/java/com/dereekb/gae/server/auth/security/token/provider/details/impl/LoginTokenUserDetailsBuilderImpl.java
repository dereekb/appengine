package com.dereekb.gae.server.auth.security.token.provider.details.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.roles.authority.GrantedAuthorityDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginPointerGrantedAuthorityBuilder;
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

	private static final String DEFAULT_ADMIN_ROLE = "ROLE_ADMIN";

	private Getter<Login> loginGetter;
	private Getter<LoginPointer> loginPointerGetter;

	private GrantedAuthorityDecoder grantedAuthorityDecoder;

	private List<GrantedAuthority> tokenAuthorities;
	private List<GrantedAuthority> anonymousAuthorities;
	
	private LoginPointerGrantedAuthorityBuilder loginPointerAuthorityBuilder = new LoginPointerGrantedAuthorityBuilderImpl();
	
	private String adminGrantedAuthority = DEFAULT_ADMIN_ROLE;

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
		this.setAnonymousAuthorities(null);
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
			tokenAuthorities = new ArrayList<>();
		}

		this.tokenAuthorities = tokenAuthorities;
	}

	public List<GrantedAuthority> getAnonymousAuthorities() {
		return this.anonymousAuthorities;
	}

	public void setAnonymousAuthorities(List<GrantedAuthority> anonymousAuthorities) {
		if (anonymousAuthorities == null) {
			anonymousAuthorities = new ArrayList<>();
		}

		this.anonymousAuthorities = anonymousAuthorities;
	}
	
	public String getAdminGrantedAuthority() {
		return adminGrantedAuthority;
	}
	
	public void setAdminGrantedAuthority(String adminGrantedAuthority) throws IllegalArgumentException {
		
		if (adminGrantedAuthority == null || adminGrantedAuthority.isEmpty()) {
			throw new IllegalArgumentException("Admin role cannot be null or empty.");
		}
		
		this.adminGrantedAuthority = adminGrantedAuthority;
	}
	
	public LoginPointerGrantedAuthorityBuilder getLoginPointerAuthorityBuilder() {
		return loginPointerAuthorityBuilder;
	}

	public void setLoginPointerAuthorityBuilder(LoginPointerGrantedAuthorityBuilder loginPointerAuthorityBuilder) {
		this.loginPointerAuthorityBuilder = loginPointerAuthorityBuilder;
	}

	// MARK: LoginTokenUserDetailsBuilder
	@Override
	public LoginTokenUserDetails buildDetails(LoginToken loginToken) throws IllegalArgumentException {
		LoginTokenUserDetails details;

		if (loginToken.isAnonymous()) {
			details = new AnonymousLoginTokenUserDetailsImpl(loginToken);
		} else {
			details = new LoginTokenUserDetailsImpl(loginToken);
		}

		return details;
	}

	// MARK: Anonymous
	private class AnonymousLoginTokenUserDetailsImpl extends LoginTokenUserDetailsImpl {

		private static final long serialVersionUID = 1L;

		private AnonymousLoginTokenUserDetailsImpl(LoginToken loginToken) throws IllegalArgumentException {
			super(loginToken);
		}

		@Override
		public Login getLogin() {
			return null;
		}

		@Override
		public LoginPointer getLoginPointer() {
			return null;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public boolean isAnonymous() {
			return true;
		}

	}

	// MARK: LoginTokenUserDetails
	private class LoginTokenUserDetailsImpl
	        implements LoginTokenUserDetails {

		private static final long serialVersionUID = 1L;

		protected final LoginToken loginToken;

		private boolean loginLoaded = false;

		private Login login = null;
		private LoginPointer loginPointer = null;

		private Set<GrantedAuthority> authorities;

		private LoginTokenUserDetailsImpl(LoginToken loginToken) throws IllegalArgumentException {
			if (loginToken == null) {
				throw new IllegalArgumentException("Token cannnot be null.");
			}

			this.loginToken = loginToken;
		}

		@Override
		public Login getLogin() {
			if (this.loginLoaded == false) {
				Long id = this.loginToken.getLogin();

				if (id != null) {
					ModelKey key = new ModelKey(id);
					this.login = LoginTokenUserDetailsBuilderImpl.this.loginGetter.get(key);
				}

				this.loginLoaded = true;
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

				if (this.loginToken.isAnonymous()) {
					this.authorities = new HashSet<>(LoginTokenUserDetailsBuilderImpl.this.anonymousAuthorities);
				} else {
					Long encodedRoles = this.loginToken.getRoles();

					if (encodedRoles != null) {
						this.authorities = LoginTokenUserDetailsBuilderImpl.this.grantedAuthorityDecoder
						        .decodeRoles(encodedRoles);
					} else {
						this.authorities = new HashSet<>();
					}

					this.authorities.addAll(LoginTokenUserDetailsBuilderImpl.this.tokenAuthorities);
				}
				
				LoginPointerType type = this.loginToken.getPointerType();
				this.authorities.addAll(LoginTokenUserDetailsBuilderImpl.this.loginPointerAuthorityBuilder.getGrantedAuthorities(type));
			}

			return this.authorities;
		}

		@Override
		public String getPassword() {
			return null; // Password not available
		}

		@Override
		public String getUsername() {
			return this.loginToken.getSubject();
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
			return false;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public LoginToken getLoginToken() {
			return this.loginToken;
		}

		@Override
		public boolean isAdministrator() {
			return this.getAuthorities().contains(adminGrantedAuthority);
		}

		@Override
		public boolean isAnonymous() {
			return false;
		}

	}

}
