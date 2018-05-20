package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.client.api.auth.system.impl.ClientSystemLoginTokenServiceRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.SystemLoginTokenFactoryConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.system.impl.SystemLoginTokenFactoryImpl;

/**
 * {@link SystemLoginTokenFactoryConfigurer} implementation for a local token
 * security type.
 *
 * @author dereekb
 *
 */
public class RemoteSystemLoginTokenFactoryConfigurerImpl
        implements SystemLoginTokenFactoryConfigurer {

	private RemoteServiceConfiguration remoteLoginService;

	public RemoteSystemLoginTokenFactoryConfigurerImpl(RemoteServiceConfiguration remoteLoginService) {
		this.setRemoteLoginService(remoteLoginService);
	}

	public RemoteServiceConfiguration getRemoteLoginService() {
		return this.remoteLoginService;
	}

	public void setRemoteLoginService(RemoteServiceConfiguration remoteLoginService) {
		if (remoteLoginService == null) {
			throw new IllegalArgumentException("remoteLoginService cannot be null.");
		}

		this.remoteLoginService = remoteLoginService;
	}

	// MARK: SystemLoginTokenFactoryConfigurer
	@Override
	public void configureSystemLoginTokenFactory(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder builder) {

		builder.comment("Login Token System");
		String systemLoginTokenServiceBeanId = appConfig.getAppBeans().getSystemLoginTokenServiceBeanId();

		builder.bean(systemLoginTokenServiceBeanId).beanClass(ClientSystemLoginTokenServiceRequestSenderImpl.class).c()
		        .ref(this.remoteLoginService.getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());

		builder.bean(appConfig.getAppBeans().getSystemLoginTokenFactoryBeanId())
		        .beanClass(SystemLoginTokenFactoryImpl.class).c().ref(systemLoginTokenServiceBeanId)
		        .ref(appConfig.getAppBeans().getAppLoginSecuritySigningServiceBeanId());

		// TODO: Add other parts!

	}

}
