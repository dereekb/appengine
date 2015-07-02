package com.thevisitcompany.gae.deprecated.model.users.login.handlers;

import com.thevisitcompany.gae.deprecated.model.users.permissions.UsersPermissionHandler;

public class LoginPermissionsHandler extends UsersPermissionHandler {

	private static final String RESPONSE_KEY = "login";
	
	@Override
	public String getResponseKey() {
		return RESPONSE_KEY;
	}

	public static String permissionsRequestString(String request) {
		LoginPermissionsHandler defaultHandler = new LoginPermissionsHandler();
		String permission = defaultHandler.getPermissionStringForRequest(request);
		return permission;
	}
	
}
