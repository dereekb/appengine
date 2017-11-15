package com.dereekb.gae.server.auth.security.model.context.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRole;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContextRoleSet;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * {@link LoginTokenModelContextRoleSet} implementation.
 * 
 * @author dereekb
 *
 */
public class LoginTokenModelContextRoleSetImpl extends AbstractLoginTokenModelContextRoleSet {

	private Set<LoginTokenModelContextRole> roles;

	public LoginTokenModelContextRoleSetImpl(LoginTokenModelContextRole... roles) {
		this(ListUtility.toList(roles));
	}

	public LoginTokenModelContextRoleSetImpl(Collection<LoginTokenModelContextRole> roles) {
		this.setRoles(roles);
	}

	// MARK: LoginTokenModelContextRoleSet
	@Override
	public Set<LoginTokenModelContextRole> getRoles() {
		return this.roles;
	}

	public void setRoles(Collection<LoginTokenModelContextRole> roles) {
		if (roles == null) {
			throw new IllegalArgumentException("roles cannot be null.");
		}

		this.roles = SetUtility.copy(roles);
		this.resetStringRoles();
	}

	@Override
	public String toString() {
		return "LoginTokenModelContextRoleSetImpl [roles=" + this.roles + "]";
	}

}
