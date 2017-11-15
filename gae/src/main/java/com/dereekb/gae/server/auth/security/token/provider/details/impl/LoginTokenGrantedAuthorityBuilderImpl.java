package com.dereekb.gae.server.auth.security.token.provider.details.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.roles.authority.GrantedAuthorityDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginPointerGrantedAuthorityBuilder;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenGrantedAuthorityBuilder;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link LoginTokenGrantedAuthorityBuilder} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenGrantedAuthorityBuilderImpl<T extends LoginToken>
        implements LoginTokenGrantedAuthorityBuilder<T> {

	public static final String DEFAULT_NEW_USER_ROLE = "ROLE_NEW_USER";

	private static final GrantedAuthority newUserAuthority = new SimpleGrantedAuthority(DEFAULT_NEW_USER_ROLE);

	private GrantedAuthorityDecoder grantedAuthorityDecoder;

	private List<GrantedAuthority> tokenAuthorities;
	private List<GrantedAuthority> anonymousAuthorities;
	private List<GrantedAuthority> newUserAuthorities = ListUtility.wrap(newUserAuthority);

	private LoginPointerGrantedAuthorityBuilder loginPointerAuthorityBuilder = new LoginPointerGrantedAuthorityBuilderImpl();

	public LoginTokenGrantedAuthorityBuilderImpl(GrantedAuthorityDecoder grantedAuthorityDecoder)
	        throws IllegalArgumentException {
		this(grantedAuthorityDecoder, null, null);
	}

	public LoginTokenGrantedAuthorityBuilderImpl(GrantedAuthorityDecoder grantedAuthorityDecoder,
	        List<GrantedAuthority> tokenAuthorities,
	        List<GrantedAuthority> anonymousAuthorities) throws IllegalArgumentException {
		super();
		this.setGrantedAuthorityDecoder(grantedAuthorityDecoder);
		this.setTokenAuthorities(tokenAuthorities);
		this.setAnonymousAuthorities(anonymousAuthorities);
	}

	public GrantedAuthorityDecoder getGrantedAuthorityDecoder() {
		return this.grantedAuthorityDecoder;
	}

	public void setGrantedAuthorityDecoder(GrantedAuthorityDecoder grantedAuthorityDecoder)
	        throws IllegalArgumentException {
		if (grantedAuthorityDecoder == null) {
			throw new IllegalArgumentException("GrantedAuthorityDecoder cannot be null.");
		}

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

	public List<GrantedAuthority> getNewUserAuthorities() {
		return this.newUserAuthorities;
	}

	public void setNewUserAuthorities(List<GrantedAuthority> newUserAuthorities) {
		if (newUserAuthorities == null) {
			throw new IllegalArgumentException("newUserAuthorities cannot be null.");
		}

		this.newUserAuthorities = newUserAuthorities;
	}

	public LoginPointerGrantedAuthorityBuilder getLoginPointerAuthorityBuilder() {
		return this.loginPointerAuthorityBuilder;
	}

	public void setLoginPointerAuthorityBuilder(LoginPointerGrantedAuthorityBuilder loginPointerAuthorityBuilder) {
		this.loginPointerAuthorityBuilder = loginPointerAuthorityBuilder;
	}

	// MARK: LoginTokenGrantedAuthorityBuilder
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(T token) {
		Set<GrantedAuthority> authorities = null;
		LoginPointerType type = token.getPointerType();

		if (token.isAnonymous()) {
			authorities = new HashSet<>(this.anonymousAuthorities);
		} else if (token.isNewUser()) {
			authorities = new HashSet<>(this.newUserAuthorities);
		} else if (type != LoginPointerType.REFRESH_TOKEN) {
			// TODO: Consider updating to better expose strategy for handling
			// different types.

			Long encodedRoles = token.getRoles();

			if (encodedRoles != null) {
				authorities = this.grantedAuthorityDecoder.decode(encodedRoles);
			} else {
				authorities = new HashSet<>();
			}

			authorities.addAll(this.tokenAuthorities);
		}

		authorities.addAll(this.loginPointerAuthorityBuilder.getGrantedAuthorities(type));

		return authorities;
	}

	@Override
	public String toString() {
		return "LoginTokenGrantedAuthorityBuilderImpl [grantedAuthorityDecoder=" + this.grantedAuthorityDecoder
		        + ", tokenAuthorities=" + this.tokenAuthorities + ", anonymousAuthorities=" + this.anonymousAuthorities
		        + ", loginPointerAuthorityBuilder=" + this.loginPointerAuthorityBuilder + "]";
	}

}
