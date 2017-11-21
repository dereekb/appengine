package com.dereekb.gae.server.auth.security.model.roles.loader.builder;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

/**
 * {@link ModelRoleSetLoaderBuilder} step that loads roles for the input model.
 * 
 * @author dereekb
 *
 */
public interface ModelRoleSetLoaderBuilderComponent<T> {

	/**
	 * Returns a list of potential roles this type can grant or modify.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelRole> getPotentialRoles();

	/**
	 * Checks whether or not a specific role is granted.
	 * 
	 * @param model
	 * @param role
	 * @return
	 */
	public boolean hasRole(T model,
	                       ModelRole role);

	/**
	 * Loads a specific role.
	 * 
	 * @param model
	 * @param role
	 * @param set
	 * @return
	 */
	public boolean loadRole(T model,
	                        ModelRole role,
	                        ModelRoleSetLoadingSet set);

	/**
	 * Modifies the input roles map.
	 * <p>
	 * Roles can be added or removed.
	 * 
	 * @param model
	 * @param set
	 */
	public void loadRoles(T model,
	                      ModelRoleSetLoadingSet set);

}
