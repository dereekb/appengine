package com.dereekb.gae.server.auth.deprecated.permissions.roles;

import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionNode;
import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionsSet;

@Deprecated
public class NewUserRole extends Role {

	private static final long serialVersionUID = 1L;
	public static final String ROLE_NAME = "ROLE_NEW_USER";
	private static final int ROLE_LEVEL = 1;
	private static final PermissionsSet permissions = new PermissionNode("newUser");

	@Override
	public PermissionsSet getPermissionsSet() {
		return permissions;
	}

	@Override
	public String getAuthority() {
		return ROLE_NAME;
	}

	@Override
	protected Integer getRoleLevel() {
		return ROLE_LEVEL;
	}

}