package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import java.util.Collections;
import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link CustomLocalModelEventListenerConfigurer} implementation that does
 * nothing.
 *
 * @author dereekb
 *
 */
public class NoOpCustomLocalModelEventListenerConfigurerImpl
        implements CustomLocalModelEventListenerConfigurer {

	@Override
	public Map<String, String> configureEventListenerEntries(AppConfiguration appConfiguration,
	                                                         LocalModelConfiguration modelConfig,
	                                                         SpringBeansXMLBuilder builder) {
		return Collections.emptyMap();
	}

}
