package com.dereekb.gae.server.auth.security.roles;

import java.util.Set;

/**
 * Contains a set of encoded roles.
 * 
 * @author dereekb
 *
 */
public interface EncodedRolesBearer {

	public Set<Integer> getEncodedRoles();

}
