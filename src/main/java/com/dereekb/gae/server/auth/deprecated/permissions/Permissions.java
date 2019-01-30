package com.dereekb.gae.server.auth.deprecated.permissions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.deprecated.permissions.roles.Role;
import com.dereekb.gae.server.auth.deprecated.permissions.roles.RoleType;
import com.googlecode.objectify.annotation.Ignore;

/**
 * Contains a set of permissions that can be deciphered by PermissionsManager.
 * @author dereekb
 *
 */
@Deprecated
public class Permissions implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<Integer> roleIdentifiers = new HashSet<Integer>(1);

	@Ignore
	private transient Set<RoleType> roleTypes;

	@Ignore
	private transient List<Role> roles;

	public void addRoleTypes(RoleType... types) {
		for (RoleType type : types) {
			this.roleIdentifiers.add(type.getBit());
		}

		this.roleTypes = null;
		this.roles = null;
	}

	public void addRoleType(RoleType type) {
		this.roleIdentifiers.add(type.getBit());
		this.roleTypes = null;
		this.roles = null;
	}

	public void removeRoleType(RoleType type) {
		this.roleIdentifiers.remove(type.getBit());
		this.roleTypes = null;
		this.roles = null;
	}

	public List<Role> getRoles() {
		List<Role> roles = this.roles;

		if (roles == null) {
			roles = this.buildRoles();
			this.roles = roles;
		}

		return roles;
	}

	private List<Role> buildRoles() {
		Set<RoleType> roleTypes = this.getRoleTypes();
		List<Role> roles = new ArrayList<Role>(roleTypes.size());

		for (RoleType roleType : roleTypes) {
			Role role = roleType.roleInstance();
			roles.add(role);
		}

		return roles;
	}

	private Set<RoleType> createRoleTypes() {
		Set<RoleType> roleTypes = new HashSet<RoleType>();

		for (Integer identifier : this.roleIdentifiers) {
			RoleType type = RoleType.typeWithBit(identifier);
			roleTypes.add(type);
		}

		return roleTypes;
	}

	public Set<RoleType> getRoleTypes() {
		Set<RoleType> roleTypes = this.roleTypes;

		if (roleTypes == null) {
			roleTypes = this.createRoleTypes();
			this.roleTypes = roleTypes;
		}

		return new HashSet<RoleType>(roleTypes);
	}

	public boolean hasRoles() {
		return (this.roleIdentifiers.size() != 0);
	}

	public Integer getRolesCount() {
		return this.roleIdentifiers.size();
	}

	public Collection<GrantedAuthority> getAuthorities() {
		List<Role> roles = this.getRoles();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.addAll(roles);
		return authorities;
    }

}
