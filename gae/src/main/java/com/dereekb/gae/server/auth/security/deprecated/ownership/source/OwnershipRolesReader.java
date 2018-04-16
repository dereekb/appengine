package com.dereekb.gae.server.auth.security.ownership.source;

import com.dereekb.gae.server.auth.security.deprecated.ownership.OwnershipRoles;

/**
 * Used for reading roles from models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
@Deprecated
public interface OwnershipRolesReader<T> {

	/**
	 * Reads {@link OwnershipRoles} values from thee input model.
	 *
	 * @param input
	 * @return
	 */
	public OwnershipRoles readRoles(T input);

}
