package com.dereekb.gae.server.api.google.cloud.storage.impl;

import com.dereekb.gae.server.api.google.cloud.storage.GoogleCloudStorageService;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.contrib.nio.testing.LocalStorageHelper;

/**
 * In-Memory {@link GoogleCloudStorageService} implementation.
 *
 * @author dereekb
 *
 * @see https://github.com/googleapis/google-cloud-java/tree/master/google-cloud-clients/google-cloud-contrib/google-cloud-nio
 */
public class InMemoryGoogleCloudStorageService
        implements GoogleCloudStorageService {

	// MARK: GoogleCloudStorageService
	@Override
	public Storage getGoogleCloudStorageService() throws RuntimeException {
		return LocalStorageHelper.getOptions().getService();
	}

}
