package com.dereekb.gae.server.auth.security.roles;

import java.util.Set;

public interface EncodedRolesBearer {

	public Set<Integer> getEncodedRoles();

}
