package com.thevisitcompany.gae.deprecated.authentication.login;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.thevisitcompany.gae.deprecated.authentication.login.security.LoginAuthentication;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSource;
import com.thevisitcompany.gae.deprecated.authentication.login.support.LoginSourceService;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.roles.Role;

/**
 * Reads
 *
 * @author dereekb
 *
 */
public class AuthenticationReader
        implements LoginSource, LoginSourceService {

	private final LoginAuthentication authentication;

	@Override
	public Login getLogin() {
		return this.authentication.getLogin();
	}

	@Override
	public LoginSource getLoginDelegate() {
		return this;
	}

	public AuthenticationReader(LoginAuthentication authentication) {
		this.authentication = authentication;
	}

	public Set<GrantedAuthority> getAuthoritiesSet() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.addAll(this.authentication.getAuthorities());
		return authorities;
	}

	public Set<String> getAuthorityNamesSet() {

		Collection<GrantedAuthority> authorities = this.authentication.getAuthorities();
		Set<String> authorityNames = new HashSet<String>();

		for (GrantedAuthority authority : authorities) {
			String authorityName = authority.getAuthority();
			authorityNames.add(authorityName);
		}

		return authorityNames;
	}

	public boolean hasRole(Role... roles) {
		boolean hasRole = false;

		Collection<Role> rolesSet = this.authentication.getRoles();

		for (Role role : roles) {
			if (rolesSet.contains(role)) {
				hasRole = true;
				break;
			}
		}

		return hasRole;
	}

	/**
	 * Returns true if the authentication has any of the given authorities.
	 *
	 * @param string
	 * @return
	 */
	public boolean hasAuthority(String... authorities) {
		boolean hasAuthority = false;

		Set<String> authorityNames = this.getAuthorityNamesSet();

		for (String authority : authorities) {

			if (authorityNames.contains(authority)) {
				hasAuthority = true;
				break;
			}
		}

		return hasAuthority;
	}

	/**
	 * Returns true if the authentication has all of the given authorities.
	 *
	 * @param string
	 * @return
	 */
	public boolean hasAuthorities(String... authorities) {
		boolean hasAuthority = (authorities.length > 0);

		Set<String> authorityNames = this.getAuthorityNamesSet();

		for (String authority : authorities) {
			if (authorityNames.contains(authority) == false) {
				hasAuthority = false;
				break;
			}
		}

		return hasAuthority;
	}

}
