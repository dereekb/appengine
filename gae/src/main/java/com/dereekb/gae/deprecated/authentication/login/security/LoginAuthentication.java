package com.thevisitcompany.gae.deprecated.authentication.login.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.Permissions;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.roles.PublicRole;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.roles.Role;

public class LoginAuthentication
        implements Authentication {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_NAME = "Public User";

	private final Login principal;
	private final Object details;
	private boolean authenticated;

	public LoginAuthentication(Login principal, Object details) {
		this.principal = principal;
		this.details = details;
		authenticated = true;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new PublicRole());

		if (principal != null) {
			Permissions permissions = principal.getPermissions();
			Collection<GrantedAuthority> permissionAuthorities = permissions.getAuthorities();
			authorities.addAll(permissionAuthorities);
		}

		return authorities;
	}

	public Collection<Role> getRoles() {

		Set<Role> roles = new HashSet<Role>();
		roles.add(new PublicRole());

		if (principal != null) {
			Permissions permissions = principal.getPermissions();
			List<Role> permissionRoles = permissions.getRoles();
			roles.addAll(permissionRoles);
		}

		return roles;
	}

	@Override
	public Object getCredentials() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getDetails() {
		return this.details;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public Login getLogin() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) {
		authenticated = isAuthenticated;
	}

	@Override
	public String getName() {
		String name = DEFAULT_NAME;

		if (principal != null) {
			name = principal.getEmail();
		}

		return name;
	}
}
