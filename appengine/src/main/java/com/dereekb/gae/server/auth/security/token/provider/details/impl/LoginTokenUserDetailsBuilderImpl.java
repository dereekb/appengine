package com.dereekb.gae.server.auth.security.token.provider.details.impl;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.misc.SecurityUtility;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetDecoder;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenGrantedAuthorityBuilder;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetailsBuilder;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserType;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;
import com.dereekb.gae.server.datastore.models.keys.utility.ModelKeySource;
import com.dereekb.gae.server.datastore.models.keys.utility.impl.ModelKeySourceImpl;
import com.dereekb.gae.server.datastore.utility.source.ModelKeyGetterSourceImpl;
import com.dereekb.gae.utilities.model.lazy.LazyLoadSource;

/**
 * {@link LoginTokenUserDetailsBuilder} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenUserDetailsBuilderImpl<T extends LoginTokenImpl>
        implements LoginTokenUserDetailsBuilder<T> {

	private static final String DEFAULT_ADMIN_ROLE = "ROLE_ADMIN";

	private Getter<Login> loginGetter;
	private Getter<LoginPointer> loginPointerGetter;

	private LoginTokenModelContextSetDecoder contextSetDecoder;
	private LoginTokenGrantedAuthorityBuilder<T> authorityBuilder;

	private String adminRole = DEFAULT_ADMIN_ROLE;

	public LoginTokenUserDetailsBuilderImpl(LoginTokenModelContextSetDecoder contextSetDecoder,
	        LoginTokenGrantedAuthorityBuilder<T> authorityBuilder) {
		this(contextSetDecoder, authorityBuilder, null, null);
	}

	public LoginTokenUserDetailsBuilderImpl(LoginTokenModelContextSetDecoder contextSetDecoder,
	        LoginTokenGrantedAuthorityBuilder<T> authorityBuilder,
	        Getter<Login> loginGetter,
	        Getter<LoginPointer> loginPointerGetter) {
		this.setContextSetDecoder(contextSetDecoder);
		this.setAuthorityBuilder(authorityBuilder);
		this.setLoginGetter(loginGetter);
		this.setLoginPointerGetter(loginPointerGetter);
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

	public LoginTokenModelContextSetDecoder getContextSetDecoder() {
		return this.contextSetDecoder;
	}

	public void setContextSetDecoder(LoginTokenModelContextSetDecoder contextSetDecoder) {
		if (contextSetDecoder == null) {
			throw new IllegalArgumentException("contextSetDecoder cannot be null.");
		}

		this.contextSetDecoder = contextSetDecoder;
	}

	public LoginTokenGrantedAuthorityBuilder<T> getAuthorityBuilder() {
		return this.authorityBuilder;
	}

	public void setAuthorityBuilder(LoginTokenGrantedAuthorityBuilder<T> authorityBuilder) {
		if (authorityBuilder == null) {
			throw new IllegalArgumentException("AuthorityBuilder cannot be null.");
		}

		this.authorityBuilder = authorityBuilder;
	}

	public String getAdminRole() {
		return this.adminRole;
	}

	public void setAdminRole(String adminRole) {
		if (adminRole == null || adminRole.isEmpty()) {
			throw new IllegalArgumentException("adminRole cannot be null or empty.");
		}

		this.adminRole = adminRole;
	}

	// MARK: LoginTokenUserDetailsBuilder
	@Override
	public LoginTokenUserDetails<T> buildDetails(DecodedLoginToken<T> decodedLoginToken)
	        throws IllegalArgumentException {
		LoginTokenUserDetails<T> details;

		if (decodedLoginToken.getLoginToken().isAnonymous()) {
			details = new AnonymousLoginTokenUserDetailsImpl(decodedLoginToken);
		} else {
			details = new LoginTokenUserDetailsImpl(decodedLoginToken);
		}

		return details;
	}

	// MARK: Anonymous
	protected class AnonymousLoginTokenUserDetailsImpl extends AbstractAnonymousLoginTokenUserDetailsImpl {

		private static final long serialVersionUID = 1L;

		protected AnonymousLoginTokenUserDetailsImpl(DecodedLoginToken<T> decodedLoginToken)
		        throws IllegalArgumentException {
			super(decodedLoginToken);
		}

	}

	// MARK: LoginTokenUserDetails
	protected class LoginTokenUserDetailsImpl extends AbstractLoginTokenUserDetailsImpl {

		private static final long serialVersionUID = 1L;

		protected LoginTokenUserDetailsImpl(DecodedLoginToken<T> decodedLoginToken) throws IllegalArgumentException {
			super(decodedLoginToken);
		}

	}

	// MARK: Abstract/Internals
	protected class AbstractAnonymousLoginTokenUserDetailsImpl extends AbstractLoginTokenUserDetailsImpl {

		private static final long serialVersionUID = 1L;

		protected AbstractAnonymousLoginTokenUserDetailsImpl(DecodedLoginToken<T> loginToken)
		        throws IllegalArgumentException {
			super(loginToken);
		}

		@Override
		public ModelKey getLoginKey() throws NoModelKeyException {
			throw new NoModelKeyException();
		}

		@Override
		public Login getLogin() {
			return null;
		}

		@Override
		public ModelKey getLoginPointerKey() throws NoModelKeyException {
			throw new NoModelKeyException();
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
		public boolean isAdministrator() {
			return false;
		}

		@Override
		public boolean isAnonymous() {
			return true;
		}

		@Override
		public LoginTokenUserType getUserType() {
			return LoginTokenUserType.ANONYMOUS;
		}

	}

	protected class AbstractLoginTokenUserDetailsImpl
	        implements LoginTokenUserDetails<T> {

		private static final long serialVersionUID = 1L;

		protected final DecodedLoginToken<T> decodedLoginToken;

		private final ModelKeySource loginKey;
		private final ModelKeySource loginPointerKey;

		private final LazyLoadSource<Login> login;
		private final LazyLoadSource<LoginPointer> loginPointer;

		private LoginTokenModelContextSet contextSet;
		private Collection<? extends GrantedAuthority> authorities;
		private Set<String> roles;

		private LoginTokenUserType userType;

		protected AbstractLoginTokenUserDetailsImpl(DecodedLoginToken<T> decodedLoginToken)
		        throws IllegalArgumentException {
			if (decodedLoginToken == null) {
				throw new IllegalArgumentException("Token cannnot be null.");
			}

			this.decodedLoginToken = decodedLoginToken;

			T token = decodedLoginToken.getLoginToken();

			this.loginKey = ModelKeySourceImpl.make(token.getLoginId());
			this.loginPointerKey = ModelKeySourceImpl.make(token.getLoginPointerId());

			this.login = ModelKeyGetterSourceImpl.makeLazySource(LoginTokenUserDetailsBuilderImpl.this.loginGetter,
			        this.loginKey);
			this.loginPointer = ModelKeyGetterSourceImpl
			        .makeLazySource(LoginTokenUserDetailsBuilderImpl.this.loginPointerGetter, this.loginPointerKey);
		}

		@Override
		public ModelKey getLoginKey() throws NoModelKeyException {
			return this.loginKey.loadModelKey();
		}

		@Override
		public Login getLogin() throws UnsupportedOperationException {
			if (this.login.hasTriedLoading() == false) {
				if (LoginTokenUserDetailsBuilderImpl.this.loginGetter == null) {
					throw new UnsupportedOperationException();
				}
			}

			return this.login.safeLoadObject();
		}

		@Override
		public ModelKey getLoginPointerKey() throws NoModelKeyException {
			return this.loginPointerKey.loadModelKey();
		}

		@Override
		public LoginPointer getLoginPointer() throws UnsupportedOperationException {
			if (this.loginPointer.hasTriedLoading() == false) {
				if (LoginTokenUserDetailsBuilderImpl.this.loginPointerGetter == null) {
					throw new UnsupportedOperationException();
				}
			}

			return this.loginPointer.safeLoadObject();
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			if (this.authorities == null) {
				this.authorities = LoginTokenUserDetailsBuilderImpl.this.authorityBuilder
				        .getAuthorities(this.getLoginToken());
			}

			return this.authorities;
		}

		public Set<String> getRoles() {
			if (this.roles == null) {
				this.roles = SecurityUtility.getRoles(this.getAuthorities());
			}

			return this.roles;
		}

		@Override
		public String getPassword() {
			return null; // Password not available
		}

		@Override
		public String getUsername() {
			return this.getLoginToken().getSubject();
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
		public T getLoginToken() {
			return this.decodedLoginToken.getLoginToken();
		}

		@Override
		public LoginTokenModelContextSet getLoginTokenModelContextSet() {
			if (this.contextSet == null) {
				T loginToken = this.getLoginToken();
				EncodedLoginTokenModelContextSet encodedSet = loginToken.getEncodedModelContextSet();
				this.contextSet = LoginTokenUserDetailsBuilderImpl.this.contextSetDecoder.decodeSet(encodedSet);
			}

			return this.contextSet;
		}

		@Override
		public DecodedLoginToken<T> getDecodedLoginToken() {
			return this.decodedLoginToken;
		}

		@Override
		public boolean isAdministrator() {
			return this.getRoles().contains(LoginTokenUserDetailsBuilderImpl.this.adminRole);
		}

		@Override
		public boolean isAnonymous() {
			return false;
		}

		@Override
		public LoginTokenUserType getUserType() {
			if (this.userType == null) {
				LoginTokenUserType userType;

				if (this.isAdministrator()) {
					userType = LoginTokenUserType.ADMINISTRATOR;
				} else {
					// Is never anonymous here due to separate type.
					userType = (this.getLoginToken().isNewUser()) ? LoginTokenUserType.NEW_USER
					        : LoginTokenUserType.USER;
				}

				this.userType = userType;
			}

			return this.userType;
		}

	}

}
