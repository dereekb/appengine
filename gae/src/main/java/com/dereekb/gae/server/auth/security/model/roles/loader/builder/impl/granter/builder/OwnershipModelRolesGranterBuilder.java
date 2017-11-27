package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;

/**
 * Builder for a {@link ModelRoleGranter} that grants roles based on ownership.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface OwnershipModelRolesGranterBuilder<T> {

	/**
	 * Creates a new granter for the input role.
	 *
	 * @param grantedRole
	 *            {@link ModelRole}. Never {@code null}.
	 * @return {@link ModelRoleGranter}. Never {@code null}.
	 */
	public ModelRoleGranter<T> makeGranterForRole(ModelRole grantedRole);

}
