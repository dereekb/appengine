package com.dereekb.gae.server.auth.security.ownership.source;

import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;

/**
 * Used for reading roles from models.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface OwnershipRolesReader<T> {

	public OwnershipRoles readRoles(T input);

}
