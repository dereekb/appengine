package com.dereekb.gae.server.auth.security.token.provider.details.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.roles.authority.GrantedAuthorityDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginPointerGrantedAuthorityBuilder;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenGrantedAuthorityBuilder;

/**
 * {@link LoginTokenGrantedAuthorityBuilder} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenGrantedAuthorityBuilderImpl
        implements LoginTokenGrantedAuthorityBuilder {

	private GrantedAuthorityDecoder grantedAuthorityDecoder;

	private List<GrantedAuthority> tokenAuthorities;
	private List<GrantedAuthority> anonymousAuthorities;

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

	public LoginPointerGrantedAuthorityBuilder getLoginPointerAuthorityBuilder() {
		return this.loginPointerAuthorityBuilder;
	}

	public void setLoginPointerAuthorityBuilder(LoginPointerGrantedAuthorityBuilder loginPointerAuthorityBuilder) {
		this.loginPointerAuthorityBuilder = loginPointerAuthorityBuilder;
	}

	// MARK: LoginTokenGrantedAuthorityBuilder
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(LoginToken token) {
		Set<GrantedAuthority> authorities = null;

		if (token.isAnonymous()) {
			authorities = new HashSet<>(this.anonymousAuthorities);
		} else {
			Long encodedRoles = token.getRoles();

			if (encodedRoles != null) {
				authorities = this.grantedAuthorityDecoder.decodeRoles(encodedRoles);
			} else {
				authorities = new HashSet<>();
			}

			authorities.addAll(this.tokenAuthorities);
		}

		LoginPointerType type = token.getPointerType();
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