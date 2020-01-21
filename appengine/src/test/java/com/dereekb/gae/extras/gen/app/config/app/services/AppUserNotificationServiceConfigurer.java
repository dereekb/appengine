package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring a {@link UserNotificationService} bean.
 *
 * @author dereekb
 *
 */
public interface AppUserNotificationServiceConfigurer {

	/**
	 * Creates a {@link UserNotificationService} bean and related components for
	 * this service.
	 *
	 * @param appConfiguration
	 *            {@link AppConfiguration}. Never {@code null}.
	 * @param builder
	 */
	public void configureUserNotificationService(AppConfiguration appConfiguration,
	                                             SpringBeansXMLBuilder builder);

}
