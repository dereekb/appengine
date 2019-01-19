package com.dereekb.gae.server.auth.old.security.role.provided;

import com.dereekb.gae.server.auth.deprecated.old.security.role.Role;

/**
 * User role.
 *
 * @author dereekb
 *
 */
@Deprecated
public class UserLoginRole
        implements Role {

	public static final String ROLE_NAME = "ROLE_USER";
	public static final Integer ROLE_ID = 1;

	@Override
	public String getRoleName() {
		return ROLE_NAME;
	}

	@Override
	public Integer getRoleIdentifier() {
		return ROLE_ID;
	}

}
