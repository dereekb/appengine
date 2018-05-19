package com.dereekb.gae.extras.gen.app.config.app.services;

import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

public interface AppModelKeyEventListenerConfigurer {

	/**
	 *
	 * @param appConfiguration
	 *            {@link AppConfiguration}. Never {@code null}.
	 * @param builder
	 *
	 * @return {@link Map} of all entries by key. Never {@code null}.
	 */
	public Map<String, String> configureModelKeyEventListenerEntries(AppConfiguration appConfiguration,
	                                                                 SpringBeansXMLBuilder builder);

}
