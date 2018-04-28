package com.dereekb.gae.extras.gen.utility.spring.security.impl;

import java.util.Set;

import com.dereekb.gae.extras.gen.utility.spring.security.RoleConfig;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * {@link RoleConfig} implementation.
 *
 * @author dereekb
 *
 */
public class HasAnyRoleConfig extends AbstractRoleConfigImpl {

	public static final String ROLE_FUNCTION = "hasAnyRole";

	private Set<String> roles;

	public HasAnyRoleConfig(String... roles) {
		this(false, roles);
	}

	public HasAnyRoleConfig(boolean not, String... roles) {
		super(not, ROLE_FUNCTION);
		this.setRoles(roles);
	}

	public static RoleConfig make(String... roles) {
		return new HasAnyRoleConfig(roles);
	}

	public static RoleConfig not(String... roles) {
		return new HasAnyRoleConfig(true, roles);
	}

	public Set<String> getRoles() {
		return this.roles;
	}

	public void setRoles(String... roles) {
		this.setRoles(SetUtility.makeSet(roles));
	}

	public void setRoles(Set<String> roles) {
		if (roles == null) {
			throw new IllegalArgumentException("roles cannot be null.");
		}

		this.roles = roles;
	}

	@Override
	protected Iterable<String> getArguments() {
		return this.roles;
	}

	@Override
	public String toString() {
		return "HasAnyRoleConfig [roles=" + this.roles + ", getAccess()=" + this.getAccess() + "]";
	}

}
