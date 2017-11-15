package com.dereekb.gae.server.auth.security.model.context.impl;

import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;

/**
 * Abstract {@link LoginTokenModelContextRoleSet} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractLoginTokenModelContextRoleSet
        implements LoginTokenModelContextRoleSet {

	private transient Set<String> stringRoles;

	// MARK: LoginTokenModelContextRoleSet
	@Override
	public boolean hasRole(LoginTokenModelContextRole role) {
		this.initStringRoles();
		return this.stringRoles.contains(role.getRole());
	}

	@Override
	public abstract Set<LoginTokenModelContextRole> getRoles();

	// MARK: Internal
	protected void resetStringRoles() {
		if (this.stringRoles != null) {
			this.stringRoles = null;
			this.initStringRoles();
		}
	}

	protected void initStringRoles() {
		if (this.stringRoles == null) {
			this.stringRoles = LoginTokenModelContextRoleSetUtility.readRoles(this.getRoles());
		}
	}

}
