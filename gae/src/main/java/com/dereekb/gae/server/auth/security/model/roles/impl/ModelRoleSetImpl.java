package com.dereekb.gae.server.auth.security.model.roles.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * {@link ModelRoleSet} implementation.
 * 
 * @author dereekb
 *
 */
public class ModelRoleSetImpl extends AbstractModelRoleSet {

	private Set<ModelRole> roles;

	public ModelRoleSetImpl(ModelRole... roles) {
		this(ListUtility.toList(roles));
	}

	public ModelRoleSetImpl(Collection<ModelRole> roles) {
		this.setRoles(roles);
	}

	// MARK: ModelRoleSet
	@Override
	public boolean isEmpty() {
		return this.roles.isEmpty();
	}
	
	@Override
	public Set<ModelRole> getRoles() {
		return this.roles;
	}

	public void setRoles(Collection<ModelRole> roles) {
		if (roles == null) {
			throw new IllegalArgumentException("roles cannot be null.");
		}

		this.roles = SetUtility.copy(roles);
		this.resetStringRoles();
	}

	@Override
	public String toString() {
		return "ModelRoleSetImpl [roles=" + this.roles + "]";
	}

}
