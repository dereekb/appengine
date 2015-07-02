package com.dereekb.gae.server.auth.deprecated.permissions.roles;

import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionNode;
import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionsSet;

public class PublicRole extends Role {

	private static final long serialVersionUID = 1L;
	public static final String ROLE_NAME = "ROLE_PUBLIC";
	private static final int ROLE_LEVEL = 0;
	private static final PermissionsSet permissions = new PermissionNode("*");	//TODO: Set Public Role permissions.

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