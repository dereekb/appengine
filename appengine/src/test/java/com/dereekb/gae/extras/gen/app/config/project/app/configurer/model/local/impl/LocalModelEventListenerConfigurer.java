package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

public interface LocalModelEventListenerConfigurer {

	/**
	 *
	 * @param appConfiguration
	 * @param modelConfig
	 * @param builder
	 *
	 * @return {@link Map} of all entries by key. Never {@code null}.
	 */
	public Map<String, String> configureEventListenerEntries(AppConfiguration appConfiguration,
	                                                         LocalModelConfiguration modelConfig,
	                                                         SpringBeansXMLBuilder builder);

}
