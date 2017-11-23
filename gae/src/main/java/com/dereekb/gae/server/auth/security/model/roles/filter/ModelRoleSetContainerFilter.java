package com.dereekb.gae.server.auth.security.model.roles.filter;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSetContainer;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;

/**
 * {@link Filter} for {@link ModelRoleSetContainer} values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRoleSetContainerFilter extends AbstractFilter<ModelRoleSetContainer> {

	private final ModelRole role;

	public ModelRoleSetContainerFilter(ModelRole role) {
		if (role == null) {
			throw new IllegalArgumentException("role cannot be null.");
		}

		this.role = role;
	}

	public ModelRole getRole() {
		return this.role;
	}

	// MARK: Filter
	@Override
	public FilterResult filterObject(ModelRoleSetContainer object) {
		ModelRoleSet roleSet = object.getRoleSet();
		return FilterResult.valueOf(roleSet.hasRole(this.role));
	}

	@Override
	public String toString() {
		return "ModelRoleSetContainerFilter [role=" + this.role + "]";
	}

}
