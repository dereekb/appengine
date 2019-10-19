package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelSearchConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link LocalModelSearchConfigurer} noop implementation.
 *
 * @author dereekb
 *
 */
public class NoOpLocalModelSearchConfigurerImpl
        implements LocalModelSearchConfigurer {

	@Override
	public boolean hasSearchComponents() {
		return false;
	}

	@Override
	public void configureSearchComponents(AppConfiguration appConfig,
	                                      LocalModelConfiguration modelConfig,
	                                      SpringBeansXMLBuilder builder) {
		// Do nothing.
	}

	@Override
	public String toString() {
		return "NoOpLocalModelSearchConfigurerImpl []";
	}

}
