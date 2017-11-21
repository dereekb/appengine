package com.dereekb.gae.server.auth.security.model.roles.loader.builder;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

/**
 * Loading set used by {@link ModelRoleSetLoaderBuilderComponent}.
 * 
 * @author dereekb
 *
 */
public interface ModelRoleSetLoadingSet {

	/**
	 * Adds a role to the set.
	 * 
	 * @param role
	 */
	public void addRole(ModelRole role);

	/**
	 * 
	 * @param roles
	 */
	public void addRoles(Iterable<ModelRole> roles);

	/**
	 * 
	 * @param role
	 */
	public void removeRole(ModelRole role);

	/**
	 * 
	 * @param roles
	 */
	public void removeRoles(Iterable<ModelRole> roles);

}
