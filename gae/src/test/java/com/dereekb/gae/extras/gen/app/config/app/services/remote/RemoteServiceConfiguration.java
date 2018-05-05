package com.dereekb.gae.extras.gen.app.config.app.services.remote;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceContextConfigurer;

/**
 * Configuration for connecting to a remote server/service.
 *
 * @author dereekb
 *
 */
public interface RemoteServiceConfiguration {

	public AppServiceConfigurationInfo getAppServiceConfigurationInfo();

	/**
	 * Returns all model groups for this remote service.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<RemoteModelConfigurationGroup> getServiceModelConfigurations();

	/**
	 * Returns the beans configuration.
	 *
	 * @return {@link RemoteServiceBeansConfiguration}. Never {@code null}.
	 */
	public RemoteServiceBeansConfiguration getServiceBeansConfiguration();

	/**
	 * Returns the configurer.
	 *
	 * @return {@link RemoteServiceContextConfigurer}. Never {@code null}.
	 */
	public RemoteServiceContextConfigurer getRemoteServiceContextConfigurer();

	/**
	 * Whether or not the service is a sibling or not.
	 * <p>
	 * This may be used for altering generation behavior for integration
	 * testing.
	 *
	 */
	public boolean isSiblingService();

}
