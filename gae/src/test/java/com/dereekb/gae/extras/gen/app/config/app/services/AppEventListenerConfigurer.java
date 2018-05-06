package com.dereekb.gae.extras.gen.app.config.app.services;

import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

public interface AppEventListenerConfigurer {

	/**
	 *
	 * @param appConfiguration
	 *            {@link AppConfiguration}. Never {@code null}.
	 * @param builder
	 *
	 * @return {@link Map} of all entries by key.
	 */
	public Map<String, String> configureEventListenerEntries(AppConfiguration appConfiguration,
	                                                         SpringBeansXMLBuilder builder);

}
