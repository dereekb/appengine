package com.dereekb.gae.extras.gen.app.config.app;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;

/**
 * App configuration used for app XML generation.
 *
 * @author dereekb
 *
 */
public interface AppConfiguration {

	public Long getAppId();

	/**
	 * Returns the human-readable name.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppName();

	/**
	 * Returns the taskqueue dedicated to this service.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppTaskQueueName();

	public AppServiceConfigurationInfo getAppServiceConfigurationInfo();

	public AppBeansConfiguration getAppBeans();

	public AppSecurityBeansConfigurer getAppSecurityBeansConfigurer();

	public AppServicesConfigurer getAppServicesConfigurer();

	public List<AppRemoteServiceConfiguration> getRemoteServices();

	public List<LocalModelConfigurationGroup> getLocalModelConfigurations();

	public List<LocalModelConfigurationGroup> getModelConfigurations();

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

}
