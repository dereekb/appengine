package com.dereekb.gae.extras.gen.app.config.app.services;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.event.event.service.EventServiceListener;

/**
 * Used for configuring a list of {@link EventServiceListener}.
 *
 * @author dereekb
 *
 */
public interface AppEventServiceListenersConfigurer {

	/**
	 * Configure any event service listeners with the builder, and return all
	 * bean ids.
	 *
	 * @param appConfiguration
	 * @param builder
	 * @return {@link List} of bean refs. Never {@code null}.
	 */
	public List<String> configureEventServiceListeners(AppConfiguration appConfiguration,
	                                                   SpringBeansXMLBuilder builder);

}
