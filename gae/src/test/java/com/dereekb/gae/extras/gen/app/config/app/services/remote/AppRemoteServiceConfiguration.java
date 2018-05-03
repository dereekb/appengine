package com.dereekb.gae.extras.gen.app.config.app.services.remote;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.AppRemoteServiceConfigurer;

/**
 * Configuration for connecting to a remote server/service.
 *
 * @author dereekb
 *
 */
public interface AppRemoteServiceConfiguration {

	public AppServiceConfigurationInfo getAppServiceConfigurationInfo();

	/**
	 * Returns all model groups for this remote service.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<AppModelConfigurationGroup> getServiceModelConfigurations();

	/**
	 * Returns the beans configuration.
	 *
	 * @return {@link AppRemoteServiceBeansConfiguration}. Never {@code null}.
	 */
	public AppRemoteServiceBeansConfiguration getServiceBeansConfiguration();

	/**
	 * Returns the configurer.
	 *
	 * @return {@link AppRemoteServiceConfigurer}. Never {@code null}.
	 */
	public AppRemoteServiceConfigurer getAppRemoteServiceConfigurer();

	/**
	 * Whether or not the service is a sibling or not.
	 * <p>
	 * This may be used for altering generation behavior for integration
	 * testing.
	 *
	 */
	public boolean isSiblingService();

}
