package com.dereekb.gae.server.api.google.cloud.storage.impl;

import com.dereekb.gae.server.api.google.cloud.storage.GoogleCloudStorageService;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

/**
 * {@link GoogleCloudStorageService} implementation.
 *
 * @author dereekb
 *
 */
public class GoogleCloudStorageServiceImpl
        implements GoogleCloudStorageService, Factory<Storage>, Source<Storage> {

	public static final String CREDENTIALS_KEY_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";

	private transient Storage storage;

	// MARK: GoogleCloudStorageService
	@Override
	public Storage getGoogleCloudStorageService() throws RuntimeException {
		return this.loadObject();
	}

	// MARK: Source
	@Override
	public Storage make() throws FactoryMakeFailureException {
		try {
			return this.loadObject();
		} catch (Exception e) {
			throw new FactoryMakeFailureException(e);
		}
	}

	@Override
	public Storage loadObject() throws RuntimeException, UnavailableSourceObjectException {
		if (this.storage == null) {
			this.storage = this.initialize();
		}

		return this.storage;
	}


	// MARK: Initialization
	private synchronized Storage initialize() throws RuntimeException {
		// Sync Check
		if (this.storage != null) {
			return this.storage;
		}

		return StorageOptions.getDefaultInstance().getService();
	}

	@Override
	public String toString() {
		return "GoogleCloudStorageServiceImpl [storage=" + this.storage + "]";
	}

}
