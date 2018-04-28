package com.dereekb.gae.extras.gen.utility.spring.security.impl;

import com.dereekb.gae.extras.gen.utility.spring.security.RoleConfig;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * {@link RoleConfig} implementation.
 *
 * @author dereekb
 *
 */
public class HasRoleConfig extends AbstractRoleConfigImpl {

	public static final String ROLE_FUNCTION = "hasRole";

	private String role;

	public HasRoleConfig(String role) {
		this(false, role);
	}

	public HasRoleConfig(boolean not, String role) {
		super(not, ROLE_FUNCTION);
		this.setRole(role);
	}

	public static RoleConfig make(String role) {
		return new HasRoleConfig(role);
	}

	public static RoleConfig make(String role,
	                              boolean not) {
		return new HasRoleConfig(not, role);
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		if (role == null) {
			throw new IllegalArgumentException("role cannot be null.");
		}

		this.role = role;
	}

	@Override
	protected Iterable<String> getArguments() {
		return SingleItem.withValue(this.role);
	}

	@Override
	public String toString() {
		return "HasRoleConfig [role=" + this.role + ", getAccess()=" + this.getAccess() + "]";
	}

}
