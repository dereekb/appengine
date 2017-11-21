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
	 *            Model. Never {@code null}.
	 * @param role
	 *            {@link ModelRole} to load. Never {@code null}.
	 * @return {@code true} if the role is granted.
	 */
	public boolean hasRole(T model,
	                       ModelRole role);

	/**
	 * Equivalent to {{@link #hasRole(Object, ModelRole)}, but also modifies the
	 * input set.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @param role
	 *            {@link ModelRole} to load. Never {@code null}.
	 * @param set
	 *            {@link ModelRoleSetLoadingSet} to modify. Never {@code null}.
	 * @return {@code true} if the role is granted.
	 */
	public boolean loadRole(T model,
	                        ModelRole role,
	                        ModelRoleSetLoadingSet set);

	/**
	 * Modifies the input roles map.
	 * <p>
	 * Roles can be added or removed from the set.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @param set
	 *            {@link ModelRoleSetLoadingSet} to modify. Never {@code null}.
	 */
	public void loadRoles(T model,
	                      ModelRoleSetLoadingSet set);

}
