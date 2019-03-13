package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.builder;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;

/**
 * Builder for a {@link ModelRoleGranter} that grants roles based on the
 * parent's roles.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ParentChildModelRoleGranterBuilder<T> {

	/**
	 * Creates a new granter for the input role if the parent also has that
	 * role.
	 *
	 * @param grantedRole
	 *            {@link ModelRole}. Never {@code null}.
	 * @return {@link ModelRoleGranter}. Never {@code null}.
	 */
	public ModelRoleGranter<T> makeGrantRoleForParentRole(ModelRole role);

	/**
	 * Creates a new granter for the input role.
	 *
	 * @param grantedRole
	 *            {@link ModelRole}. Never {@code null}.
	 * @param parentRole
	 *            {@link ModelRole}. Never {@code null}.
	 * @return {@link ModelRoleGranter}. Never {@code null}.
	 */
	public ModelRoleGranter<T> makeGrantRoleForParentRole(ModelRole grantedRole,
	                                                      ModelRole parentRole);

}
