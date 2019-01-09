package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Configurer for setting up app initialization beans.
 *
 * @author dereekb
 *
 */
public interface AppServerInitializationConfigurer {

	/**
	 * Configures the server initialization beans.
	 *
	 * @param appConfig
	 * @param builder
	 */
	public void configureServerInitializationComponents(AppConfiguration appConfig,
	                                                    SpringBeansXMLBuilder builder);

}
