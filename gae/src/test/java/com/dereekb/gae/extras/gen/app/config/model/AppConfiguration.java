package com.dereekb.gae.extras.gen.app.config.model;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;

public interface AppConfiguration {

	public Long getAppId();

	public String getAppName();

	public String getAppServiceName();

	public String getAppTaskQueueName();

	public String getAppVersion();

	public AppBeansConfiguration getAppBeans();

	public AppSecurityBeansConfigurer getAppSecurityBeansConfigurer();

	public List<AppModelConfigurationGroup> getModelConfigurations();

	/**
	 * Whether or not the server is the initial server.
	 * <p>
	 * These services may not rely on initialization components.
	 * <p>
	 * If {@link #isLoginServer()} is true, this is usually true too.
	 *
	 * @return {@code true} if a root server.
	 */
	public boolean isRootServer();

	public boolean isLoginServer();

	/**
	 * Returns the root API path for this app.
	 */
	public String getRootAppApiPath();

}
