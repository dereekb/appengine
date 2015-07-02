package com.thevisitcompany.gae.deprecated.model.storage.permissions;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.permissions.CrudsPermissionHandler;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.evaluator.PermissionEventHandlerFunction;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.evaluator.PermissionsEvent;

public class StoragePermissionHandler extends CrudsPermissionHandler {

	private static final String RESPONSE_KEY = "storage";

	@Override
	public String getResponseKey() {
		return RESPONSE_KEY;
	}

	@PermissionEventHandlerFunction("upload")
	public boolean canUpload(PermissionsEvent event) {
		return this.hasPermissionStringCheck(event);
	}

	@PermissionEventHandlerFunction("upload/link")
	public boolean canGetUploadLink(PermissionsEvent event) {
		return this.hasPermissionStringCheck(event);
	}

	@Override
	public String getPermissionStringPrefix() {
		String permissionPrefix = super.getPermissionStringPrefix() + "." + RESPONSE_KEY;
		return permissionPrefix;
	}

	public static String permissionsRequestString(String request) {
		StoragePermissionHandler defaultHandler = new StoragePermissionHandler();
		String permission = defaultHandler.getPermissionStringForRequest(request);
		return permission;
	}

}