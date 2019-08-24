package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Optional configurer for debug components.
 *
 * @author dereekb
 *
 */
public interface AppDebugApiConfigurer {

	/**
	 * Creates any debug API controllers.
	 */
	public void configureDebugApiController(AppConfiguration appConfiguration,
	                                        SpringBeansXMLBuilder builder);

}
