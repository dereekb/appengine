package com.dereekb.gae.server.auth.old.security.role;

/**
 * Represents an arbitrary security role.
 *
 * @author dereekb
 */
public interface Role {

	/**
	 * Name used by this role.
	 *
	 * @return a name with a "ROLE_" prefix
	 */
	public String getRoleName();

	/**
	 * Unique identifier for this role.
	 *
	 * @return a positive role identifier.
	 */
	public Integer getRoleIdentifier();

}
