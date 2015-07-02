package com.thevisitcompany.gae.deprecated.model.users.account.handlers;

import com.thevisitcompany.gae.deprecated.model.users.permissions.UsersPermissionHandler;

public class AccountPermissionsHandler extends UsersPermissionHandler {

	private static final String RESPONSE_KEY = "account";
	
	@Override
	public String getResponseKey() {
		return RESPONSE_KEY;
	}

	public static String permissionsRequestString(String request) {
		AccountPermissionsHandler defaultHandler = new AccountPermissionsHandler();
		String permission = defaultHandler.getPermissionStringForRequest(request);
		return permission;
	}
	
}
