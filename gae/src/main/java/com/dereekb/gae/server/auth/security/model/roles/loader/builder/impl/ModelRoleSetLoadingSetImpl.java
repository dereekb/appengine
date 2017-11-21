package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.impl.ModelRoleSetUtility;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoadingSet;
import com.dereekb.gae.utilities.collections.set.DelegatedSet;

/**
 * {@link ModelRoleSetLoadingSet} implementation.
 * 
 * @author dereekb
 *
 */
public class ModelRoleSetLoadingSetImpl
        implements ModelRoleSetLoadingSet {

	private DelegatedSet<ModelRole> set;

	public ModelRoleSetLoadingSetImpl() {
		this(ModelRoleSetUtility.makeRoleSet());
	}

	public ModelRoleSetLoadingSetImpl(DelegatedSet<ModelRole> set) {
		super();
		this.setSet(set);
	}

	public DelegatedSet<ModelRole> getSet() {
		return this.set;
	}

	public void setSet(DelegatedSet<ModelRole> set) {
		if (set == null) {
			throw new IllegalArgumentException("set cannot be null.");
		}

		this.set = set;
	}

	// MARK: ModelRoleSetLoadingSet
	@Override
	public void addRole(ModelRole role) {
		this.set.add(role);
	}

	@Override
	public void addRoles(Iterable<ModelRole> roles) {
		this.set.addAll(roles);
	}

	@Override
	public void removeRole(ModelRole role) {
		this.set.remove(role);
	}

	@Override
	public void removeRoles(Iterable<ModelRole> roles) {
		this.set.removeAll(roles);
	}

	@Override
	public String toString() {
		return "ModelRoleSetLoadingSetImpl [set=" + this.set + "]";
	}

}
