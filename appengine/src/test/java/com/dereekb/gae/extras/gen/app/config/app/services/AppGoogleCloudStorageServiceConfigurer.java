package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.api.google.cloud.storage.GoogleCloudStorageService;

/**
 * Used for configuring a {@link GoogleCloudStorageService} bean.
 *
 * @author dereekb
 *
 */
public interface AppGoogleCloudStorageServiceConfigurer {

	/**
	 * Creates a {@link GoogleCloudStorageService} bean and related components
	 * for this service.
	 *
	 * @param appConfiguration
	 *            {@link AppConfiguration}. Never {@code null}.
	 * @param builder
	 */
	public void configureGoogleCloudStorageService(AppConfiguration appConfiguration,
	                                               SpringBeansXMLBuilder builder);

}
