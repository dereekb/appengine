package com.dereekb.gae.server.auth.security.model.roles.loader.builder;

import java.util.Collection;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;

/**
 * Loading set used by {@link ModelRoleSetLoaderBuilderComponent}.
 * <p>
 * Used to build a {@link ModelRoleSet} from the results.
 *
 * @author dereekb
 *
 */
public interface ModelRoleSetLoadingSet {

	/**
	 * Adds a role to the set.
	 *
	 * @param role
	 *            {@link ModelRole}. Never {@code null}.
	 */
	public void addRole(ModelRole role);

	/**
	 * Adds multiple roles to the set.
	 *
	 * @param roles
	 *            {@link Iterable}. Never {@code null}.
	 */
	public void addRoles(Iterable<ModelRole> roles);

	/**
	 * Removes a role from the set.
	 *
	 * @param role
	 *            {@link ModelRole}. Never {@code null}.
	 */
	public void removeRole(ModelRole role);

	/**
	 * Removes multiple roles from the set.
	 *
	 * @param roles
	 *            {@link Iterable}. Never {@code null}.
	 */
	public void removeRoles(Iterable<ModelRole> roles);

	/**
	 * Whether or not there are any roles in this set.
	 *
	 * @return {@code true} if empty.
	 */
	public boolean isEmpty();

	/**
	 * Whether or not the role is in the set.
	 *
	 * @param role
	 *            {@link ModelRole}. Never {@code null}.
	 * @return {@code true} if the role is in the set.
	 */
	public boolean containsRole(ModelRole role);

	/**
	 * Returns all roles in a collection.
	 *
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelRole> getRoles();

}
