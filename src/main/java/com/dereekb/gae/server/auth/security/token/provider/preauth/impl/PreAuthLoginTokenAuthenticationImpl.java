package com.dereekb.gae.server.auth.security.token.provider.preauth.impl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.preauth.PreAuthLoginTokenAuthentication;

/**
 * {@link PreAuthLoginTokenAuthentication} implementation.
 *
 * @author dereekb
 *
 */
public class PreAuthLoginTokenAuthenticationImpl<T extends LoginToken>
        implements PreAuthLoginTokenAuthentication<T> {

	private static final long serialVersionUID = 1L;

	protected final DecodedLoginToken<T> token;
	protected final WebAuthenticationDetails details;

	public PreAuthLoginTokenAuthenticationImpl(DecodedLoginToken<T> token, WebAuthenticationDetails details)
	        throws IllegalArgumentException {
		this.token = token;
		this.details = details;
	}

	@Override
	public String getName() {
		return "PRE_LOGIN";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public DecodedLoginToken<T> getCredentials() {
		return this.token;
	}

	@Override
	public WebAuthenticationDetails getDetails() {
		return this.details;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		throw new IllegalArgumentException("Cannot authenticate here.");
	}

	@Override
	public String toString() {
		return "PreAuthLoginTokenAuthenticationImpl [details=" + this.details + ", token=" + this.token + "]";
	}

}
