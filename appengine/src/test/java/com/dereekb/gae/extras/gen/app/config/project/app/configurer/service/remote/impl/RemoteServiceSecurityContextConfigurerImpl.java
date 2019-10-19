package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl;

import java.util.List;

import com.dereekb.gae.client.api.auth.model.roles.impl.ClientModelRolesServiceRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.impl.RemoteModelConfigurationGroupImpl;
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

		// MARK: System Token
		String appSystemTokenFactory = appConfig.getAppBeans().getSystemLoginTokenFactoryBeanId();
		String serviceSystemTokenFactory = appRemoteServiceConfiguration.getServiceBeansConfiguration()
		        .getServiceSystemTokenFactoryBeanId();

		builder.alias(appSystemTokenFactory, serviceSystemTokenFactory);

		// MARK: Client Model Roles Context Service
		builder.comment("Service Model Roles Security");
		builder.bean(appRemoteServiceConfiguration.getServiceBeansConfiguration().getClientModelRolesServiceBeanId())
			.beanClass(ClientModelRolesServiceRequestSenderImpl.class)
			.c()
			.ref(appConfig.getAppBeans().getModelKeyTypeConverterId())
			.ref(appRemoteServiceConfiguration.getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());

		// MARK: Security Contexts
		builder.comment("Security Context Service Entries");

		List<RemoteModelConfiguration> remoteModelConfigs = RemoteModelConfigurationGroupImpl.readModelConfigurations(appRemoteServiceConfiguration.getServiceModelConfigurations());

		for (RemoteModelConfiguration remoteModelConfig : remoteModelConfigs)  {
			builder.bean(remoteModelConfig.getBeansConfiguration().getModelSecurityContextServiceEntryBeanId())
			.factoryBean(appConfig.getAppBeans().getUtilityBeans().getClientLoginTokenModelContextServiceEntryFactoryBeanId())
			.factoryMethod("makeServiceEntry")
			.c()
			.value(remoteModelConfig.getModelType())
			.ref(appRemoteServiceConfiguration.getServiceBeansConfiguration().getClientModelRolesServiceBeanId());
		}
	}

}
