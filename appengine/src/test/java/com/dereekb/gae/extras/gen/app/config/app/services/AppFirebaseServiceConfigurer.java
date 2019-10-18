package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring a {@link FirebaseService} bean.
 *
 * @author dereekb
 *
 */
public interface AppFirebaseServiceConfigurer {

	/**
	 * Creates a {@link FirebaseService} bean and related components for
	 * this service.
	 *
	 * @param appConfiguration
	 *            {@link AppConfiguration}. Never {@code null}.
	 * @param builder
	 */
	public void configureFirebaseService(AppConfiguration appConfiguration,
	                                     SpringBeansXMLBuilder builder);

}
