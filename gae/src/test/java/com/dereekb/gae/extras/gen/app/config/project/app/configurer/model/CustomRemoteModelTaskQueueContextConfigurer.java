package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.context.ContextRemoteConfigurationsGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used by {@link ContextRemoteConfigurationsGenerator}.
 *
 * @author dereekb
 *
 */
public interface CustomRemoteModelTaskQueueContextConfigurer {

	/**
	 * Configures the components for the service that will be used by the
	 * taskqueue context.
	 */
	public void configureRemoteModelTaskQueueContextComponents(AppConfiguration appConfig,
	                                                           AppModelConfiguration modelConfig,
	                                                           SpringBeansXMLBuilder builder);

}
