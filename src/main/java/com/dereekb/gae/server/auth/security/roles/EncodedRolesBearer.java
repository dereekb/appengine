package com.dereekb.gae.server.auth.security.roles;


/**
 * Contains a set of encoded roles.
 *
 * @author dereekb
 *
 */
public interface EncodedRolesBearer {

	/**
	 * Returns a {@link Long} containing all encoded roles.
	 *
	 * @return {@link Long}. Never {@code null}.
	 */
	public Long getEncodedRoles();

}
