package com.dereekb.gae.server.auth.security.model.roles.impl;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link ModelRole} implementation.
 * 
 * @author dereekb
 *
 */
public class ModelRoleImpl
        implements ModelRole {

	private String role;

	public ModelRoleImpl(String role) {
		this.setRole(role);
	}

	@Override
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		if (StringUtility.isEmptyString(role)) {
			throw new IllegalArgumentException("role cannot be null or empty.");
		}

		this.role = role;
	}

	@Override
	public String toString() {
		return "ModelRoleImpl [role=" + this.role + "]";
	}

}