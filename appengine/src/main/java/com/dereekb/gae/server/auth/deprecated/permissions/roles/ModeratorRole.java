package com.dereekb.gae.server.auth.deprecated.permissions.roles;

import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionNode;
import com.dereekb.gae.server.auth.deprecated.permissions.components.PermissionsSet;

@Deprecated
public class ModeratorRole extends Role {

	private static final long serialVersionUID = 1L;
	public static final String ROLE_NAME = "ROLE_MODERATOR";
	private static final int ROLE_LEVEL = 6;
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