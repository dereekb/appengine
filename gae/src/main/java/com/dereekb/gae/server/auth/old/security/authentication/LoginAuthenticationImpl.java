package com.dereekb.gae.server.auth.old.security.authentication;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * {@link Authentication} implementation that uses {@link Login} and related
 * elements.
 *
 * @author dereekb
 */
public final class LoginAuthenticationImpl
        implements LoginAuthentication {

	private static final long serialVersionUID = 1L;

	/**
	 * Current login.
	 */
	private final Login login;

	/**
	 * The type of authentication that was used to log the user in.
	 *
	 * Is used by {@link LoginAuthenticationProvider} as a key.
	 */
	private String loginSystem;

	/**
	 * Pointer used by the system to login, if applicable.
	 */
	private LoginPointer loginPointer;

	/**
	 * Collection of {@link GrantedAuthority} for this authentication.
	 */
	private Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

	/**
	 * Whether or not this is currently authenticated.
	 */
	private boolean authenticated;

	public LoginAuthenticationImpl(Login login) {
		this.login = login;
	}

	@Override
    public Login getLogin() {
		return this.login;
	}

	@Override
    public String getLoginSystem() {
		return this.loginSystem;
	}

	public void setLoginSystem(String loginSystem) {
		this.loginSystem = loginSystem;
	}

	@Override
    public LoginPointer getLoginPointer() {
		return this.loginPointer;
	}

	public void setLoginPointer(LoginPointer loginPointer) {
		this.loginPointer = loginPointer;
	}

	@Override
	public String getName() {
		Long identifier = this.login.getIdentifier();
		return identifier.toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		if (authorities == null) {
			authorities = Collections.emptyList();
		}

		this.authorities = authorities;
	}

	@Override
	public Object getCredentials() {
		return this.loginPointer;
	}

	@Override
	public Object getDetails() {
		return this.loginSystem;
	}

	@Override
	public Object getPrincipal() {
		return this.login;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
	}

}
