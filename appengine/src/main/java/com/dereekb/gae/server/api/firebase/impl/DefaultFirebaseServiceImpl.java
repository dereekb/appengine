package com.dereekb.gae.server.api.firebase.impl;

import com.dereekb.gae.server.api.firebase.FirebaseService;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;
import com.google.firebase.FirebaseApp;

/**
 * Default provider for an {@link FirebaseApp} instance.
 *
 * @author dereekb
 *
 * @deprecated Use {@link FirebaseServiceImpl} instead for more control over
 *             initialization.
 */
@Deprecated
public class DefaultFirebaseServiceImpl
        implements FirebaseService, Factory<FirebaseApp>, Source<FirebaseApp> {

	public static final String CREDENTIALS_KEY_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";

	private transient FirebaseApp firebaseApp;

	private String databaseUrl;

	public DefaultFirebaseServiceImpl(String databaseUrl) {
		super();
		this.setDatabaseUrl(databaseUrl);
	}

	public String getDatabaseUrl() {
		return this.databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	// MARK: FirebaseService
	@Override
	public FirebaseApp getFirebaseApp() {
		return this.loadObject();
	}

	// MARK: Source
	@Override
	public FirebaseApp make() throws FactoryMakeFailureException {
		try {
			return this.loadObject();
		} catch (Exception e) {
			throw new FactoryMakeFailureException(e);
		}
	}

	@Override
	public FirebaseApp loadObject() throws RuntimeException, UnavailableSourceObjectException {
		if (this.firebaseApp == null) {
			this.firebaseApp = this.initialize();
		}

		return this.firebaseApp;
	}

	// MARK: Initialization
	private FirebaseApp initialize() throws RuntimeException {

		/*
		 * FirebaseApp.getInstance() will return the default database that is
		 * generated with
		 * using the default setup configuration specified
		 */
		FirebaseApp defaultInstance = FirebaseApp.getInstance();

		this.assertIsExpectedDatabase(defaultInstance);

		return defaultInstance;
	}

	private void assertIsExpectedDatabase(FirebaseApp instance) throws RuntimeException {
		String databaseUrl = instance.getOptions().getDatabaseUrl();

		if (!StringUtility.isEmptyString(this.databaseUrl) && !databaseUrl.equals(this.databaseUrl)) {
			throw new RuntimeException("Unexpected firebase app database url: " + databaseUrl);
		}
	}

	@Override
	public String toString() {
		return "FirebaseServiceImpl [" + this.databaseUrl + "]";
	}

}
