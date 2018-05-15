package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceSecurityContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link RemoteServiceSecurityContextConfigurer} implementation that just uses
 * the app's security token.
 *
 * @author dereekb
 *
 */
public class RemoteServiceSecurityContextConfigurerImpl
        implements RemoteServiceSecurityContextConfigurer {

	// MARK: RemoteServiceSecurityContextConfigurer
	@Override
	public void configureRemoteServiceSecurityComponents(AppConfiguration appConfig,
	                                                     RemoteServiceConfiguration appRemoteServiceConfiguration,
	                                                     SpringBeansXMLBuilder builder) {

		String appSystemTokenFactory = appConfig.getAppBeans().getSystemLoginTokenFactoryBeanId();
		String serviceSystemTokenFactory = appRemoteServiceConfiguration.getServiceBeansConfiguration()
		        .getServiceSystemTokenFactoryBeanId();

		builder.alias(appSystemTokenFactory, serviceSystemTokenFactory);
	}

}
