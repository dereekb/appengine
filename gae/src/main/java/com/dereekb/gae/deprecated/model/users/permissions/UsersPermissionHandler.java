package com.thevisitcompany.gae.deprecated.model.users.permissions;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.permissions.CrudsPermissionHandler;

public class UsersPermissionHandler extends CrudsPermissionHandler{

	private static final String RESPONSE_KEY = "users";

	@Override
	public String getResponseKey() {
		return RESPONSE_KEY;
	}

	@Override
	public String getPermissionStringPrefix() {
		String permissionPrefix = super.getPermissionStringPrefix() + "." + RESPONSE_KEY;
		return permissionPrefix;
	}

	public static String permissionsRequestString(String request) {
		UsersPermissionHandler defaultHandler = new UsersPermissionHandler();
		String permission = defaultHandler.getPermissionStringForRequest(request);
		return permission;
	}
	
}