package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.impl;

import com.dereekb.gae.client.api.model.crud.builder.impl.ClientCreateRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientDeleteRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientReadRequestSenderImpl;
import com.dereekb.gae.client.api.model.crud.builder.impl.ClientUpdateRequestSenderImpl;
import com.dereekb.gae.client.api.model.extension.search.query.builder.impl.ClientQueryRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelSpringContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * Builder for a {@link RemoteModelSpringContextConfigurer} implementation
 * that builds client components and configuration.
 *
 * @author dereekb
 *
 */
public class ClientRemoteModelSpringContextConfigurerBuilder {

	// Read
	private boolean createClientReadService = true;
	private AppSpringContextType clientReadServiceContext = AppSpringContextType.SHARED;

	// Edit
	private boolean createClientEditService = true;
	private AppSpringContextType clientEditServiceContext = AppSpringContextType.TASKQUEUE;

	// Query
	private boolean createClientQueryService = true;
	private AppSpringContextType clientQueryServiceContext = AppSpringContextType.TASKQUEUE;

	public boolean isCreateClientReadService() {
		return this.createClientReadService;
	}

	public void setCreateClientReadService(boolean createClientReadService) {
		this.createClientReadService = createClientReadService;
	}

	public AppSpringContextType getClientReadServiceContext() {
		return this.clientReadServiceContext;
	}

	public void setClientReadServiceContext(AppSpringContextType clientReadServiceContext) {
		if (clientReadServiceContext == null) {
			throw new IllegalArgumentException("clientReadServiceContext cannot be null.");
		}

		this.clientReadServiceContext = clientReadServiceContext;
	}

	public boolean isCreateClientEditService() {
		return this.createClientEditService;
	}

	public void setCreateClientEditService(boolean createClientEditService) {
		this.createClientEditService = createClientEditService;
	}

	public AppSpringContextType getClientEditServiceContext() {
		return this.clientEditServiceContext;
	}

	public void setClientEditServiceContext(AppSpringContextType clientEditServiceContext) {
		if (clientEditServiceContext == null) {
			throw new IllegalArgumentException("clientEditServiceContext cannot be null.");
		}

		this.clientEditServiceContext = clientEditServiceContext;
	}

	public boolean isCreateClientQueryService() {
		return this.createClientQueryService;
	}

	public void setCreateClientQueryService(boolean createClientQueryService) {
		this.createClientQueryService = createClientQueryService;
	}

	public AppSpringContextType getClientQueryServiceContext() {
		return this.clientQueryServiceContext;
	}

	public void setClientQueryServiceContext(AppSpringContextType clientQueryServiceContext) {
		if (clientQueryServiceContext == null) {
			throw new IllegalArgumentException("clientQueryServiceContext cannot be null.");
		}

		this.clientQueryServiceContext = clientQueryServiceContext;
	}

	// MARK: Factory
	public static RemoteModelSpringContextConfigurer makeDefault() {
		return new ClientRemoteModelSpringContextConfigurerBuilder().make();
	}

	public RemoteModelSpringContextConfigurer make() {

		HashMapWithList<AppSpringContextType, RemoteModelSpringContextConfigurer> configurers = new HashMapWithList<AppSpringContextType, RemoteModelSpringContextConfigurer>();

		if (this.createClientReadService) {
			configurers.add(this.clientReadServiceContext,
			        new ClientRemoteModelReadContextConfigurerImpl(this.clientReadServiceContext));
		}

		if (this.createClientEditService) {
			configurers.add(this.clientEditServiceContext,
			        new ClientRemoteModelEditContextConfigurerImpl(this.clientEditServiceContext));
		}

		RemoteModelSpringContextConfigurerImpl configurer = new RemoteModelSpringContextConfigurerImpl(configurers);
		return configurer;
	}

	// MARK: Internal
	protected class ClientRemoteModelReadContextConfigurerImpl extends AbstractRemoteModelSpringContextConfigurer {

		public ClientRemoteModelReadContextConfigurerImpl(AppSpringContextType springContext) {
			super(springContext);
		}

		// MARK: AbstractRemoteModelSpringContextConfigurer
		@Override
		public void configureRemoteModelContextComponents(AppConfiguration appConfig,
		                                                  RemoteServiceConfiguration remoteServiceConfig,
		                                                  RemoteModelConfiguration modelConfig,
		                                                  SpringBeansXMLBuilder builder) {

			String readServiceBeanId = modelConfig.getModelClientReadServiceBeanId();

			builder.bean(readServiceBeanId).beanClass(ClientReadRequestSenderImpl.class).c()
			        .ref(modelConfig.getModelDataConverterBeanId())
			        .ref(appConfig.getAppBeans().getModelKeyTypeConverterId())
			        .ref(remoteServiceConfig.getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());

		}

	}

	protected class ClientRemoteModelEditContextConfigurerImpl extends AbstractRemoteModelSpringContextConfigurer {

		public ClientRemoteModelEditContextConfigurerImpl(AppSpringContextType springContext) {
			super(springContext);
		}

		// MARK: AbstractRemoteModelSpringContextConfigurer
		@Override
		public void configureRemoteModelContextComponents(AppConfiguration appConfig,
		                                                  RemoteServiceConfiguration remoteServiceConfig,
		                                                  RemoteModelConfiguration modelConfig,
		                                                  SpringBeansXMLBuilder builder) {

			if (modelConfig.hasCreateService()) {
				String createServiceBeanId = modelConfig.getModelClientCreateServiceBeanId();

				builder.bean(createServiceBeanId).beanClass(ClientCreateRequestSenderImpl.class).c()
				        .ref(modelConfig.getModelDataConverterBeanId())
				        .ref(appConfig.getAppBeans().getModelKeyTypeConverterId()).ref(remoteServiceConfig
				                .getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());
			}

			if (modelConfig.hasUpdateService()) {
				String updateServiceBeanId = modelConfig.getModelClientUpdateServiceBeanId();

				builder.bean(updateServiceBeanId).beanClass(ClientUpdateRequestSenderImpl.class).c()
				        .ref(modelConfig.getModelDataConverterBeanId())
				        .ref(appConfig.getAppBeans().getModelKeyTypeConverterId()).ref(remoteServiceConfig
				                .getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());
			}

			if (modelConfig.hasDeleteService()) {
				String deleteServiceBeanId = modelConfig.getModelClientDeleteServiceBeanId();

				builder.bean(deleteServiceBeanId).beanClass(ClientDeleteRequestSenderImpl.class).c()
				        .ref(modelConfig.getModelDataConverterBeanId())
				        .ref(appConfig.getAppBeans().getModelKeyTypeConverterId()).ref(remoteServiceConfig
				                .getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());
			}

		}

	}

	protected class ClientRemoteModelQueryContextConfigurerImpl extends AbstractRemoteModelSpringContextConfigurer {

		public ClientRemoteModelQueryContextConfigurerImpl(AppSpringContextType springContext) {
			super(springContext);
		}

		// MARK: AbstractRemoteModelSpringContextConfigurer
		@Override
		public void configureRemoteModelContextComponents(AppConfiguration appConfig,
		                                                  RemoteServiceConfiguration remoteServiceConfig,
		                                                  RemoteModelConfiguration modelConfig,
		                                                  SpringBeansXMLBuilder builder) {

			String queryServiceBeanId = modelConfig.getModelClientQueryServiceBeanId();

			builder.bean(queryServiceBeanId).beanClass(ClientQueryRequestSenderImpl.class).c()
			        .ref(modelConfig.getModelDataConverterBeanId())
			        .ref(appConfig.getAppBeans().getModelKeyTypeConverterId())
			        .ref(remoteServiceConfig.getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());

		}

	}

}
