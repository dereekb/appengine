package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppFirebaseServiceConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.api.firebase.impl.DefaultFirebaseServiceImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.env.EnvStringUtility;

/**
 * {@link AppFirebaseServiceConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class AppFirebaseServiceConfigurerImpl
        implements AppFirebaseServiceConfigurer {

	/**
	 * Environmental variable that is read for the service account file name/path.
	 */
	public static final String SERVICE_ACCOUNT_FILE_ENV_VAR = "FIREBASE_SERVICE_ACCOUNT_FILE_PATH";

	private boolean useServiceAccountFilePathForProduction = false;

	/**
	 * Default databse url to use.
	 */
	private String databaseUrl;

	/**
	 * Database URL that is used if not in a production environment.
	 */
	private String developmentDatabaseUrl;

	public AppFirebaseServiceConfigurerImpl(String databaseUrl) {
		this(databaseUrl, null);
	}

	public AppFirebaseServiceConfigurerImpl(String databaseUrl, String developmentDatabaseUrl) {
		super();
		this.setDatabaseUrl(developmentDatabaseUrl);
		this.setDevelopmentDatabaseUrl(developmentDatabaseUrl);
	}

	public boolean isUseServiceAccountFilePathForProduction() {
		return this.useServiceAccountFilePathForProduction;
	}

	public void setUseServiceAccountFilePathForProduction(boolean useServiceAccountFilePathForProduction) {
		this.useServiceAccountFilePathForProduction = useServiceAccountFilePathForProduction;
	}

	public String getDatabaseUrl() {
		return this.databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		if (StringUtility.isEmptyString(databaseUrl)) {
			throw new IllegalArgumentException("databaseUrl cannot be null or empty.");
		}

		this.databaseUrl = databaseUrl;
	}

	public String getDevelopmentDatabaseUrl() {
		return this.developmentDatabaseUrl;
	}

	public void setDevelopmentDatabaseUrl(String developmentDatabaseUrl) {
		this.developmentDatabaseUrl = developmentDatabaseUrl;
	}

	// MARK: AppFirebaseServiceConfigurer
	@Override
	public void configureFirebaseService(AppConfiguration appConfiguration,
	                                     SpringBeansXMLBuilder builder) {
		String databaseUrl = this.databaseUrl;
		String serviceAccountKeyFilePath;

		boolean isProduction = EnvStringUtility.isProduction();

		if (!isProduction && !StringUtility.isEmptyString(this.developmentDatabaseUrl)) {
			databaseUrl = this.developmentDatabaseUrl;
		}

		if (isProduction && this.useServiceAccountFilePathForProduction) {
			serviceAccountKeyFilePath = EnvStringUtility.readProdEnv(SERVICE_ACCOUNT_FILE_ENV_VAR);
		} else {
			serviceAccountKeyFilePath = EnvStringUtility.tryReadEnv(SERVICE_ACCOUNT_FILE_ENV_VAR);
		}

		String firebaseServiceBeanId = appConfiguration.getAppBeans().getFirebaseServiceBeanId();
		builder.bean(firebaseServiceBeanId).beanClass(DefaultFirebaseServiceImpl.class).c().value(databaseUrl)
		        .value(serviceAccountKeyFilePath);
	}

	@Override
	public String toString() {
		return "AppFirebaseServiceConfigurerImpl [useServiceAccountFilePathForProduction="
		        + this.useServiceAccountFilePathForProduction + ", databaseUrl=" + this.databaseUrl
		        + ", developmentDatabaseUrl=" + this.developmentDatabaseUrl + "]";
	}

}
