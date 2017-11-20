package com.dereekb.gae.server.auth.security.model.roles.impl;

import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;

/**
 * Abstract {@link ModelRoleSet} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractModelRoleSet
        implements ModelRoleSet {

	private transient Set<String> stringRoles;

	// MARK: ModelRoleSet
	@Override
	public boolean hasRole(ModelRole role) {
		this.initStringRoles();
		return this.stringRoles.contains(role.getRole());
	}

	@Override
	public abstract Set<ModelRole> getRoles();

	// MARK: Internal
	protected void resetStringRoles() {
		if (this.stringRoles != null) {
			this.stringRoles = null;
			this.initStringRoles();
		}
	}

	protected void initStringRoles() {
		if (this.stringRoles == null) {
			this.stringRoles = ModelRoleSetUtility.readRoles(this.getRoles());
		}
	}

}
