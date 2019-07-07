package com.dereekb.gae.extras.gen.app.config.app;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;

/**
 * App configuration used for app XML generation.
 *
 * @author dereekb
 *
 */
public interface AppConfiguration {

	/**
	 * Returns the registered app id.
	 *
	 * @return {@link Long}. Never {@code null}.
	 */
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

	/**
	 * Returns the app secret. Is only used by remote apps.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppSecret();

	/**
	 * Returns the app system key.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppSystemKey();

	/**
	 * Returns the app's admin email account.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppAdminEmail();

	/**
	 * Returns the app's domain name.
	 * <p>
	 * I.E. dereekb.com
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppDomain();

	/**
	 * Returns the app's proxy URL for development.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppDevelopmentProxyUrl();

	/**
	 * Returns the app's host URL for development.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppDevelopmentServerHostUrl();

	// public AppGenEnvConfiguration getAppGenEnvConfiguration();

	public AppServiceConfigurationInfo getAppServiceConfigurationInfo();

	public AppBeansConfiguration getAppBeans();

	public AppSecurityBeansConfigurer getAppSecurityBeansConfigurer();

	public AppServicesConfigurer getAppServicesConfigurer();

	public boolean hasRemoteServices();

	public List<RemoteServiceConfiguration> getRemoteServices();

	public List<LocalModelConfigurationGroup> getLocalModelConfigurations();

	public List<RemoteModelConfigurationGroup> getRemoteModelConfigurations();

	public List<? extends AppModelConfigurationGroup> getModelConfigurations();

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
