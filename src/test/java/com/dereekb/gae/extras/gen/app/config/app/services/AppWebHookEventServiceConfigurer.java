package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.event.webhook.listener.WebHookEventSubmitter;

/**
 * Used for configuring a service's web hooks and communication with an event
 * server.
 * <p>
 * Implementations may leverage local resources, or a dedicated remote server.
 *
 * @author dereekb
 *
 */
public interface AppWebHookEventServiceConfigurer {

	/**
	 * Creates the {@link WebHookEventSubmitter} bean and related components for
	 * this service.
	 * <p>
	 * For typical services that have a remote event service relay, this submits
	 * those events to that service.
	 *
	 * @param appConfiguration
	 *            {@link AppConfiguration}. Never {@code null}.
	 * @param builder
	 */
	public void configureWebHookEventSubmitter(AppConfiguration appConfiguration,
	                                           SpringBeansXMLBuilder builder);

}
