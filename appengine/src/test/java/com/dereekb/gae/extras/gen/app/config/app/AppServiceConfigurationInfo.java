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
	 * Returns the api version (I.E. "v1")
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getApiVersion();

	/**
	 * Returns the root app service path for this app.
	 * <p>
	 * Example: v1-dot-service-dot-example.appspot.com
	 */
	public String getAppServicePath();

	/**
	 * Returns the full API path for this service relative to the full domain.
	 * <p>
	 * Example: example.appspot.com/api/exampleservice/v1
	 */
	public String getFullDomainAppApiPath();

	/**
	 * Returns the root API path for this service relative to the full domain.
	 * <p>
	 * This API path is by design and includes /api, the service name, and the api version.
	 * <p>
	 * Example: /api/exampleservice/v1
	 */
	public String getFullDomainRootAppApiPath();

}
