package com.dereekb.gae.extras.gen.app.config.app;

/**
 * Basic service configuration.
 *
 * @author dereekb
 *
 */
public interface AppServiceConfigurationInfo {

	/**
	 * Returns the App Project ID (I.E. gae-test-project)
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppProjectId();

	/**
	 * Returns the app service name (I.E. login)
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppServiceName();

	/**
	 * Returns the version (I.E. "v1")
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppVersion();

	/**
	 * Returns the root API path for this service.
	 */
	public String getRootAppApiPath();

	/**
	 * Returns the full API path for this service.
	 */
	public String getFullAppApiPath();

}
