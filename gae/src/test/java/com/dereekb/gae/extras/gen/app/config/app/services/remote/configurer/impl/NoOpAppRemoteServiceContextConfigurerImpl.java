package com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.AppRemoteServiceContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppRemoteServiceContextConfigurer} that performs no configuration.
 *
 * @author dereekb
 *
 */
public class NoOpAppRemoteServiceContextConfigurerImpl
        implements AppRemoteServiceContextConfigurer {

	private static final NoOpAppRemoteServiceContextConfigurerImpl SINGLETON = new NoOpAppRemoteServiceContextConfigurerImpl();

	private NoOpAppRemoteServiceContextConfigurerImpl() {}

	public static NoOpAppRemoteServiceContextConfigurerImpl make() {
		return SINGLETON;
	}

	@Override
	public void configureContextComponents(AppConfiguration appConfig,
	                                       SpringBeansXMLBuilder builder) {
		// Do nothing.
	}

}
