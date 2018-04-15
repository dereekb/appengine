package com.dereekb.gae.server.auth.security.ownership;

/**
 * Interface for components that bear a {@link OwnershipRoles} item.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface OwnershipRolesBearer {

	/**
	 * Returns the ownership roles for this token.
	 *
	 * @return {@link OwnershipRoles}. Never {@code null}.
	 */
	@Deprecated
	public OwnershipRoles getOwnershipRoles();

}
