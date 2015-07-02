package com.thevisitcompany.gae.deprecated.model.users.user.handlers;

import com.thevisitcompany.gae.deprecated.model.users.permissions.UsersPermissionHandler;

public class UserPermissionsHandler extends UsersPermissionHandler {

	private static final String RESPONSE_KEY = "user";
	
	@Override
	public String getResponseKey() {
		return RESPONSE_KEY;
	}

	public static String permissionsRequestString(String request) {
		UserPermissionsHandler defaultHandler = new UserPermissionsHandler();
		String permission = defaultHandler.getPermissionStringForRequest(request);
		return permission;
	}
	
}
