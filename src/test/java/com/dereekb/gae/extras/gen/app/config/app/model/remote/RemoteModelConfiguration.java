package com.dereekb.gae.extras.gen.app.config.app.model.remote;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelContextConfigurer;

/**
 * {@link AppModelConfiguration} extension for remote models.
 *
 * @author dereekb
 *
 */
public interface RemoteModelConfiguration
        extends AppModelConfiguration, RemoteModelCrudsConfiguration, RemoteModelBeansConfiguration {

	// Custom Configuration
	/**
	 * Returns the custom configuration for configuring model contexts.
	 *
	 * @return {@link RemoteModelContextConfigurer}. Never {@code null}.
	 */
	public RemoteModelContextConfigurer getCustomModelContextConfigurer();

}
