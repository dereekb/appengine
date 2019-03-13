package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.utility.GenFolder;

/**
 * Used for configuring the remote models for a particular service.
 *
 * @author dereekb
 *
 */
public interface RemoteServiceModelsContextConfigurer {

	/**
	 * Generate a new folder of model configurations.
	 *
	 * @return {@link GenFolder}. Never {@code null}.
	 */
	public GenFolder configureRemoteServiceModelComponents(AppSpringContextType springContext,
	                                                       AppConfiguration appConfig,
	                                                       RemoteServiceConfiguration appRemoteServiceConfiguration);

}
