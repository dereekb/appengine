package com.dereekb.gae.server.auth.old.security.role.provided;

import com.dereekb.gae.server.auth.deprecated.old.security.role.Role;

/**
 * Administrator role.
 *
 * @author dereekb
 *
 */
@Deprecated
public class AdminLoginRole
        implements Role {

	public static final String ROLE_NAME = "ROLE_ADMIN";
	public static final Integer ROLE_ID = Integer.MAX_VALUE;

	@Override
	public String getRoleName() {
		return ROLE_NAME;
	}

	@Override
	public Integer getRoleIdentifier() {
		return ROLE_ID;
	}

}
