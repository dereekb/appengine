package com.dereekb.gae.server.auth.deprecated.permissions.roles;

import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionNode;
import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionsSet;

public class AdminRole extends Role {

	private static final long serialVersionUID = 1L;
	public static final String ROLE_NAME = "ROLE_ADMIN";
	private static final int ROLE_LEVEL = 10;
	private static final PermissionsSet permissions = new PermissionNode("*");

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
