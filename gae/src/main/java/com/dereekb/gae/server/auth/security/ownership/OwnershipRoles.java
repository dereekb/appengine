package com.dereekb.gae.server.auth.security.ownership;

import java.util.Set;

import com.dereekb.gae.server.auth.security.model.roles.ModelRole;

/**
 * Collection of ownership roles.
 *
 * @author dereekb
 *
 * @deprecated Replaced by {@link ModelRole} and related components.
 */
@Deprecated
public interface OwnershipRoles {

	/**
	 * Returns the owner id.
	 *
	 * @return {@link String}. Can be {@code null}, but never empty.
	 */
	public String getOwnerId();

	/**
	 * Returns all roles.
	 *
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getOwnerRoles();

	/**
	 * Returns all additional roles. Should not include the result of
	 * {@link #getOwnerId()}.
	 *
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getAdditionalRoles();

}
