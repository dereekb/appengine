package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.context.ContextRemoteConfigurationsGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used by {@link ContextRemoteConfigurationsGenerator}.
 *
 * @author dereekb
 *
 */
public interface CustomRemoteModelSharedContextConfigurer {

	/**
	 * Configures the components for the service that will be used by all
	 * spring contexts.
	 *
	 * @param appConfig
	 * @param modelConfig
	 * @param builder
	 */
	public void configureRemoteModelSharedContextComponents(AppConfiguration appConfig,
	                                                        LocalModelConfiguration modelConfig,
	                                                        SpringBeansXMLBuilder builder);

}
