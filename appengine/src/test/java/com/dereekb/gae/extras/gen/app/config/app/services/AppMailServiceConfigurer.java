package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring a server's mail service.
 *
 * @author dereekb
 *
 */
public interface AppMailServiceConfigurer {

	/**
	 * Creates a {@link MailService} bean and related components for
	 * this service.
	 *
	 * @param appConfiguration
	 *            {@link AppConfiguration}. Never {@code null}.
	 * @param builder
	 */
	public void configureMailService(AppConfiguration appConfiguration,
	                                 SpringBeansXMLBuilder builder);

}
