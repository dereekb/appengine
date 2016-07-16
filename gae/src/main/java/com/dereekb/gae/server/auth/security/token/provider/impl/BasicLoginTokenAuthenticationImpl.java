package com.dereekb.gae.server.auth.security.token.provider.impl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.BasicLoginTokenAuthentication;

/**
 * {@link BasicLoginTokenAuthentication} implementation
 *
 * @author dereekb
 *
 */
public class BasicLoginTokenAuthenticationImpl
        implements BasicLoginTokenAuthentication {

	private static final long serialVersionUID = 1L;

	private final LoginToken token;
	private final WebAuthenticationDetails details;

	public BasicLoginTokenAuthenticationImpl(LoginToken token, WebAuthenticationDetails details) {
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
	public LoginToken getCredentials() {
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
		return "BasicLoginTokenAuthenticationImpl [details=" + this.details + ", token=" + this.token + "]";
	}

}
