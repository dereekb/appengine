package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServerInitializationConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppServerInitializationConfigurer} that does nothing.
 *
 * @author dereekb
 *
 */
public class NoopAppServerInitializationConfigurerImpl implements AppServerInitializationConfigurer {

	@Override
	public void configureContextInitializationComponents(AppConfiguration appConfig,
	                                                     SpringBeansXMLBuilder builder) {
	}

	@Override
	public void configureServerInitializationComponents(AppConfiguration appConfig,
	                                                    SpringBeansXMLBuilder builder) {
	}

}
