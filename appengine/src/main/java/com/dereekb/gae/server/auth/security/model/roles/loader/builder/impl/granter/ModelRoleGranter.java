package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

// MARK: Internal
/**
 * {@link ModelRole} granter that can be asked whether or not permissions for
 * the specified model should be given in the current context.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleGranter<T> {

	public ModelRole getGrantedRole();

	public boolean hasRole(T model);

}