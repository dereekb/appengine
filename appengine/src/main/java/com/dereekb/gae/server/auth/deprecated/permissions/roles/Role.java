package com.dereekb.gae.server.auth.deprecated.permissions.roles;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionsSet;

public abstract class Role implements GrantedAuthority, Comparable<Role> {

	private static final long serialVersionUID = 1L;

	public abstract PermissionsSet getPermissionsSet();

	protected abstract Integer getRoleLevel();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		String authority = this.getAuthority();
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		String authority = this.getAuthority();
		if (authority == null) {
			if (other.getAuthority() != null)
				return false;
		} else if (!authority.equals(other.getAuthority()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Role o) {
		
		int result = 1;
		
		if (o != null) {
			Integer roleLevel= this.getRoleLevel();
			Integer otherRoleLevel = o.getRoleLevel();
			result = roleLevel.compareTo(otherRoleLevel);
		}
	
		return result;
	}
	
}
