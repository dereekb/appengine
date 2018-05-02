package com.dereekb.gae.extras.gen.app.config.app.services.remote;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;

/**
 * Configuration for connecting to a remote server/service.
 *
 * @author dereekb
 *
 */
public interface AppRemoteServiceConfiguration {

	public AppServiceConfigurationInfo getAppServiceConfigurationInfo();

	public List<AppModelConfigurationGroup> getServiceModelConfigurations();

	/**
	 * Whether or not the service is a sibling or not.
	 * <p>
	 * This may be used for altering generation behavior for integration
	 * testing.
	 *
	 */
	public boolean isSiblingService();

}
