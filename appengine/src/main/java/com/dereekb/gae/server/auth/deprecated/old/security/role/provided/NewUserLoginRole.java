package com.dereekb.gae.server.auth.old.security.role.provided;

import com.dereekb.gae.server.auth.deprecated.old.security.role.Role;

/**
 * New user role.
 *
 * @author dereekb
 */
@Deprecated
public class NewUserLoginRole
        implements Role {

	public static final String ROLE_NAME = "ROLE_NEW_USER";
	public static final Integer ROLE_ID = 0;

	@Override
	public String getRoleName() {
		return ROLE_NAME;
	}

	@Override
	public Integer getRoleIdentifier() {
		return ROLE_ID;
	}

}
