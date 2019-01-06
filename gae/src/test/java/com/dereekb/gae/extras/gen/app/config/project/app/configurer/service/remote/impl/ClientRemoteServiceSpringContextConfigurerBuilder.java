package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl;

import com.dereekb.gae.client.api.model.extension.link.impl.ClientLinkRequestSenderImpl;
import com.dereekb.gae.client.api.server.schedule.impl.ClientScheduleTaskServiceRequestSenderFactory;
import com.dereekb.gae.client.api.server.schedule.impl.ClientScheduleTaskServiceRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.impl.ClientApiRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.impl.ClientRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.security.impl.SecuredClientApiRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceSpringContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * Builder for a {@link RemoteServiceSpringContextConfigurer} implementation
 * that builds client components and configuration for a remote service.
 * <p>
 * These do not reference any specific models, but are general components.
 *
 * @author dereekb
 *
 */
public class ClientRemoteServiceSpringContextConfigurerBuilder {

	private boolean createClientApiRequestSender = true;
	private AppSpringContextType clientApiRequestSenderContext = AppSpringContextType.SHARED;

	private boolean createClientScheduleTaskService = true;
	private AppSpringContextType clientScheduleTaskServiceContext = AppSpringContextType.TASKQUEUE;

	private boolean createClientLinkService = true;
	private AppSpringContextType clientLinkServiceContext = AppSpringContextType.TASKQUEUE;

	public boolean isCreateClientApiRequestSender() {
		return this.createClientApiRequestSender;
	}

	public void setCreateClientApiRequestSender(boolean createClientApiRequestSender) {
		this.createClientApiRequestSender = createClientApiRequestSender;
	}

	public AppSpringContextType getClientApiRequestSenderContext() {
		return this.clientApiRequestSenderContext;
	}

	public void setClientApiRequestSenderContext(AppSpringContextType clientApiRequestSenderContext) {
		if (clientApiRequestSenderContext == null) {
			throw new IllegalArgumentException("clientApiRequestSenderContext cannot be null.");
		}

		this.clientApiRequestSenderContext = clientApiRequestSenderContext;
	}

	public boolean isCreateClientLinkService() {
		return this.createClientLinkService;
	}

	public void setCreateClientLinkService(boolean createClientLinkService) {
		this.createClientLinkService = createClientLinkService;
	}

	public AppSpringContextType getClientLinkServiceContext() {
		return this.clientLinkServiceContext;
	}

	public void setClientLinkServiceContext(AppSpringContextType clientLinkServiceContext) {
		if (clientLinkServiceContext == null) {
			throw new IllegalArgumentException("clientLinkServiceContext cannot be null.");
		}

		this.clientLinkServiceContext = clientLinkServiceContext;
	}

	// MARK: Factory
	public static RemoteServiceSpringContextConfigurer makeDefault() {
		return new ClientRemoteServiceSpringContextConfigurerBuilder().make();
	}

	public RemoteServiceSpringContextConfigurer make() {

		HashMapWithList<AppSpringContextType, RemoteServiceSpringContextConfigurer> configurers = new HashMapWithList<AppSpringContextType, RemoteServiceSpringContextConfigurer>();

		if (this.createClientApiRequestSender) {
			RemoteServiceSpringContextConfigurer clientApiRequestConfigurer = new ClientRemoteServiceSpringContextConfigurerImpl(
			        this.clientApiRequestSenderContext);
			configurers.add(this.clientApiRequestSenderContext, clientApiRequestConfigurer);
		}

		if (this.createClientScheduleTaskService) {
			RemoteServiceSpringContextConfigurer clientScheduleTaskServiceConfigurer = new ScheduleTaskServiceConfigurerImpl(
			        this.clientScheduleTaskServiceContext);
			configurers.add(this.clientScheduleTaskServiceContext, clientScheduleTaskServiceConfigurer);
		}

		if (this.createClientLinkService) {
			RemoteServiceSpringContextConfigurer clientLinkServiceConfigurer = new ClientRemoteServiceLinkServiceConfigurerImpl(
			        this.clientApiRequestSenderContext);
			configurers.add(this.clientLinkServiceContext, clientLinkServiceConfigurer);
		}

		RemoteServiceSpringContextConfigurerImpl configurer = new RemoteServiceSpringContextConfigurerImpl(configurers);
		return configurer;
	}

	// MARK: Internal
	protected class ClientRemoteServiceSpringContextConfigurerImpl extends AbstractRemoteServiceSpringContextConfigurer {

		public ClientRemoteServiceSpringContextConfigurerImpl() {
			super();
		}

		public ClientRemoteServiceSpringContextConfigurerImpl(AppSpringContextType springContext) {
			super(springContext);
		}

		// MARK: AbstractRemoteServiceSpringContextConfigurer
		@Override
		public void configureRemoteServiceContextComponents(AppConfiguration appConfig,
		                                                    RemoteServiceConfiguration appRemoteServiceConfiguration,
		                                                    SpringBeansXMLBuilder builder) {

			String baseApiUrl = appRemoteServiceConfiguration.getAppServiceConfigurationInfo().getFullAppApiPath();

			String clientRequestSenderBeanId = appRemoteServiceConfiguration.getServiceBeansConfiguration()
			        .getClientRequestSenderBeanId();

			builder.bean(clientRequestSenderBeanId).beanClass(ClientRequestSenderImpl.class).c().value(baseApiUrl);

			String clientApiRequestSenderBeanId = appRemoteServiceConfiguration.getServiceBeansConfiguration()
			        .getClientApiRequestSenderBeanId();

			builder.bean(clientApiRequestSenderBeanId).beanClass(ClientApiRequestSenderImpl.class).c()
			        .ref(clientRequestSenderBeanId);

			String serviceSystemTokenFactory = appRemoteServiceConfiguration.getServiceBeansConfiguration()
			        .getServiceSystemTokenFactoryBeanId();
			String securedClientApiRequestSenderBeanId = appRemoteServiceConfiguration.getServiceBeansConfiguration()
			        .getSecuredClientApiRequestSenderBeanId();

			builder.bean(securedClientApiRequestSenderBeanId).beanClass(SecuredClientApiRequestSenderImpl.class).c()
			        .ref(clientApiRequestSenderBeanId).ref(serviceSystemTokenFactory);

		}

	}

	public static class ScheduleTaskServiceConfigurerImpl extends AbstractRemoteServiceSpringContextConfigurer {

		public ScheduleTaskServiceConfigurerImpl() {
			super();
		}

		public ScheduleTaskServiceConfigurerImpl(AppSpringContextType springContext) {
			super(springContext);
		}

		// MARK: AbstractRemoteServiceSpringContextConfigurer
		@Override
		public void configureRemoteServiceContextComponents(AppConfiguration appConfig,
		                                                    RemoteServiceConfiguration appRemoteServiceConfiguration,
		                                                    SpringBeansXMLBuilder builder) {

			String clientScheduleTaskServiceBeanId = appRemoteServiceConfiguration.getServiceBeansConfiguration()
			        .getClientScheduleTaskServiceBeanId();

			String productionClientScheduleTaskServiceBeanId = "production"
			        + StringUtility.firstLetterUpperCase(clientScheduleTaskServiceBeanId);

			String clientScheduleTaskServiceFactoryBeanId = clientScheduleTaskServiceBeanId + "Factory";

			builder.bean(productionClientScheduleTaskServiceBeanId)
			        .beanClass(ClientScheduleTaskServiceRequestSenderImpl.class).c().ref(appRemoteServiceConfiguration
			                .getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());

			builder.bean(clientScheduleTaskServiceFactoryBeanId)
			        .beanClass(ClientScheduleTaskServiceRequestSenderFactory.class).property("productionSingleton")
			        .ref(productionClientScheduleTaskServiceBeanId);

			builder.bean(clientScheduleTaskServiceBeanId).factoryBean(clientScheduleTaskServiceFactoryBeanId)
			        .factoryMethod("make");

			/*
			builder.bean(clientScheduleTaskServiceBeanId).beanClass(ClientScheduleTaskServiceRequestSenderImpl.class)
			        .c().ref(appRemoteServiceConfiguration.getServiceBeansConfiguration()
			                .getSecuredClientApiRequestSenderBeanId());
			  */

		}

	}

	protected class ClientRemoteServiceLinkServiceConfigurerImpl extends AbstractRemoteServiceSpringContextConfigurer {

		public ClientRemoteServiceLinkServiceConfigurerImpl() {
			super();
		}

		public ClientRemoteServiceLinkServiceConfigurerImpl(AppSpringContextType springContext) {
			super(springContext);
		}

		// MARK: AbstractRemoteServiceSpringContextConfigurer
		@Override
		public void configureRemoteServiceContextComponents(AppConfiguration appConfig,
		                                                    RemoteServiceConfiguration appRemoteServiceConfiguration,
		                                                    SpringBeansXMLBuilder builder) {

			builder.bean(appRemoteServiceConfiguration.getServiceBeansConfiguration().getClientModelLinkServiceBeanId())
			        .beanClass(ClientLinkRequestSenderImpl.class).c()
			        .ref(appConfig.getAppBeans().getModelKeyTypeConverterId()).ref(appRemoteServiceConfiguration
			                .getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());

		}

	}

}
