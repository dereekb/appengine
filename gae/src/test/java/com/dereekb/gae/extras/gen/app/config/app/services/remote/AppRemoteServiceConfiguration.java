package com.dereekb.gae.extras.gen.app.config.app.services.remote;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;

/**
 * Configuration for connecting to a remote server/service.
 *
 * @author dereekb
 *
 */
public interface AppRemoteServiceConfiguration
        extends AppServiceConfigurationInfo {

	/**
	 * Whether or not the microservice is a sibling or not.
	 * <p>
	 * This may be used for altering generation behavior for integration
	 * testing.
	 *
	 */
	public boolean isSiblingMicroservice();

}
