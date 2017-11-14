package com.dereekb.gae.server.auth.security.model.context;

import java.util.Set;

/**
 * Contains a list of roles and options available for a
 * {@link LoginTokenModelContext}.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextRoleSet {

	/**
	 * Checks if the role exists in this set.
	 * 
	 * @param role
	 *            {@link LoginTokenModelContextRole}. Never {@code null}.
	 * @return {@code true} if the role is contained.
	 */
	public boolean hasRole(LoginTokenModelContextRole role);

	/**
	 * Returns the set of context roles.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<LoginTokenModelContextRole> getRoles();

}
