package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppDebugApiConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppDebugApiConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class NoOpAppDebugConfigurerImpl
        implements AppDebugApiConfigurer {

	@Override
	public void configureDebugApiController(AppConfiguration appConfiguration,
	                                        SpringBeansXMLBuilder builder) {
		// Do nothing.
	}

}
