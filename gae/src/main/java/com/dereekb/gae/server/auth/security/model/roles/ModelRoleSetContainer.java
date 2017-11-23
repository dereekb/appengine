package com.dereekb.gae.server.auth.security.model.roles;

/**
 * Object that contains a {@link ModelRoleSet}.
 *
 * @author dereekb
 *
 */
public interface ModelRoleSetContainer {

	/**
	 * Returns the set of roles.
	 *
	 * @return {@link ModelSet}. Never {@code null}.
	 */
	public ModelRoleSet getRoleSet();

}
