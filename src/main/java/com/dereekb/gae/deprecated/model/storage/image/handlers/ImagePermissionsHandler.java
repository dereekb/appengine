package com.thevisitcompany.gae.deprecated.model.storage.image.handlers;

import com.thevisitcompany.gae.deprecated.model.storage.permissions.StoragePermissionHandler;

public class ImagePermissionsHandler extends StoragePermissionHandler {

	private static final String RESPONSE_KEY = "image";

	@Override
	public String getResponseKey() {
		return RESPONSE_KEY;
	}

}
