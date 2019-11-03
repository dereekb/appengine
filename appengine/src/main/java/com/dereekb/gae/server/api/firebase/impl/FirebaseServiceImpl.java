package com.dereekb.gae.server.api.firebase.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dereekb.gae.server.api.firebase.FirebaseService;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseOptions.Builder;

/**
 * Primary provider for an {@link FirebaseApp} instance.
 *
 * @author dereekb
 *
 * @see https://firebase.google.com/docs/admin/setup
 */
public class FirebaseServiceImpl
        implements FirebaseService, Factory<FirebaseApp>, Source<FirebaseApp> {

	public static final String CREDENTIALS_KEY_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";

	private static final String DEFAULT_INSTANCE_NAME = "gae";

	/**
	 * Optional key file path to specify. If provided, the key will attempt to
	 * be loaded/read from here.
	 */
	private String serviceAccountKeyFilePath;

	/**
	 * Firebase Database URL
	 */
	private String databaseUrl;

	/**
	 * The instance name to use.
	 *
	 */
	private String instanceName = DEFAULT_INSTANCE_NAME;

	private transient FirebaseApp firebaseApp;

	public FirebaseServiceImpl(String databaseUrl) {
		super();
		this.setDatabaseUrl(databaseUrl);
	}

	public FirebaseServiceImpl(String databaseUrl, String serviceAccountKeyFilePath) {
		this(databaseUrl);
		this.setServiceAccountKeyFilePath(serviceAccountKeyFilePath);
	}

	public String getServiceAccountKeyFilePath() {
		return this.serviceAccountKeyFilePath;
	}

	public void setServiceAccountKeyFilePath(String serviceAccountKeyFilePath) {
		this.serviceAccountKeyFilePath = serviceAccountKeyFilePath;
	}

	public String getDatabaseUrl() {
		return this.databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		if (databaseUrl == null) {
			throw new IllegalArgumentException("databaseUrl cannot be null.");
		}

		this.databaseUrl = databaseUrl;
	}

	public String getInstanceName() {
		return this.instanceName;
	}

	public void setInstanceName(String instanceName) {
		if (StringUtility.isEmptyString(instanceName)) {
			throw new IllegalArgumentException("instanceName cannot be null.");
		}

		this.instanceName = instanceName;
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
	private synchronized FirebaseApp initialize() throws RuntimeException {
		// Sync Check
		if (this.firebaseApp != null) {
			return this.firebaseApp;
		}

		Builder optionsBuilder = new FirebaseOptions.Builder().setDatabaseUrl(this.getDatabaseUrl());

		// Set Credentials
		try {
			if (!StringUtility.isEmptyString(this.serviceAccountKeyFilePath)) {
				FileInputStream serviceAccount = new FileInputStream(this.serviceAccountKeyFilePath);
				optionsBuilder = optionsBuilder.setCredentials(GoogleCredentials.fromStream(serviceAccount));
			} else {
				optionsBuilder = optionsBuilder.setCredentials(GoogleCredentials.getApplicationDefault());
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		FirebaseOptions options = optionsBuilder.build();
		return FirebaseApp.initializeApp(options, this.instanceName);
	}

	@Override
	public String toString() {
		return "FirebaseServiceImpl [serviceAccountKeyFilePath=" + this.serviceAccountKeyFilePath + ", databaseUrl="
		        + this.databaseUrl + "]";
	}

}
