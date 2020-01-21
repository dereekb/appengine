package com.dereekb.gae.server.api.google.cloud.storage;

import com.google.cloud.storage.Storage;

/**
 * Accessor for a configured {@link Storage} instance.
 *
 * @author dereekb
 *
 */
public interface GoogleCloudStorageService {

	/**
	 * Returns an initialized {@link Storage} value.
	 *
	 * @return {@link Storage}. Never {@code null}.
	 * @throws RuntimeException
	 *             thrown if the app could not be initialized or is unavailable.
	 */
	public Storage getGoogleCloudStorageService() throws RuntimeException;

}
