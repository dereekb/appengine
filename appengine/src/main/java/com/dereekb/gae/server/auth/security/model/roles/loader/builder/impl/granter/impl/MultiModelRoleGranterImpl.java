package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link ModelRoleGranter} that works for a set of granters.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class MultiModelRoleGranterImpl<T>
        implements ModelRoleGranter<T> {

	private ModelRole role;
	private List<ModelRoleGranter<T>> granters;

	public MultiModelRoleGranterImpl(ModelRole role, List<ModelRoleGranter<T>> granters) {
		super();
		this.setRole(role);
		this.setGranters(granters);
	}

	public ModelRole getRole() {
		return this.role;
	}

	public void setRole(ModelRole role) {
		if (role == null) {
			throw new IllegalArgumentException("role cannot be null.");
		}

		this.role = role;
	}

	public List<ModelRoleGranter<T>> getGranters() {
		return this.granters;
	}

	public void setGranters(List<ModelRoleGranter<T>> granters) {
		if (granters == null) {
			throw new IllegalArgumentException("granters cannot be null.");
		}

		this.granters = ListUtility.copy(granters);
	}

	// MARK: ModelRoleGranter
	@Override
	public ModelRole getGrantedRole() {
		return this.role;
	}

	@Override
	public boolean hasRole(T model) {
		for (ModelRoleGranter<T> granter : this.granters) {
			if (granter.hasRole(model)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "MultiModelRoleGranterImpl [role=" + this.role + ", granters=" + this.granters + "]";
	}

}
