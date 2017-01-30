package com.dereekb.gae.server.auth.security.misc;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class SecurityUtility {

	public static boolean containsRole(Iterable<? extends GrantedAuthority> authorities,
	                                   String role) {
		for (GrantedAuthority grantedAuthority : authorities) {
			if (role.equals(grantedAuthority.getAuthority())) {
				return true;
			}
		}

		return false;
	}

	public static Set<String> getRoles(Iterable<? extends GrantedAuthority> authorities) {
		Set<String> roles = new HashSet<String>();

		for (GrantedAuthority grantedAuthority : authorities) {
			roles.add(grantedAuthority.getAuthority());
		}

		return roles;
	}

}
