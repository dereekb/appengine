package com.dereekb.gae.server.auth.security.model.roles;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.server.auth.security.model.context.LoginTokenModelContext;

/**
 * Contains a list of roles and options available for a
 * {@link LoginTokenModelContext}.
 * 
 * @author dereekb
 *
 */
public interface ModelRoleSet {

	/**
	 * Whether or not the role set is empty.
	 * 
	 * @return {@code true} if empty.
	 */
	public boolean isEmpty();

	/**
	 * Checks if the role exists in this set.
	 * 
	 * @param role
	 *            {@link ModelRole}. Never {@code null}.
	 * @return {@code true} if the role is contained.
	 */
	public boolean hasRole(ModelRole role);

	/**
	 * Returns the set of context roles.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Collection<ModelRole> getRoles();

}
