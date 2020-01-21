package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppGoogleCloudStorageServiceConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.api.google.cloud.storage.impl.GoogleCloudStorageServiceImpl;

/**
 * {@link AppGoogleCloudStorageServiceConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class AppGoogleCloudStorageServiceConfigurerImpl
        implements AppGoogleCloudStorageServiceConfigurer {

	// MARK: AppGoogleCloudStorageServiceConfigurer
	@Override
	public void configureGoogleCloudStorageService(AppConfiguration appConfiguration,
	                                               SpringBeansXMLBuilder builder) {
		String googleCloudStorageServiceBeanId = appConfiguration.getAppBeans().getGoogleCloudStorageServiceBeanId();
		builder.bean(googleCloudStorageServiceBeanId).beanClass(GoogleCloudStorageServiceImpl.class);
	}

}
