package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl;

import com.dereekb.gae.client.api.service.sender.impl.ClientApiRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.impl.ClientRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.security.impl.SecuredClientApiRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceSpringContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Builder for a {@link RemoteServiceSpringContextConfigurer} implementation
 * that builds client components and configuration.
 *
 * @author dereekb
 *
 */
public class ClientRemoteServiceSpringContextConfigurerBuilder {

	public RemoteServiceSpringContextConfigurer make() {
		return new ClientRemoteServiceSpringContextConfigurerImpl();
	}

	protected class ClientRemoteServiceSpringContextConfigurerImpl extends AbstractRemoteServiceSpringContextConfigurer {

		// MARK: AbstractRemoteServiceSpringContextConfigurer
		@Override
		public void configureRemoteServiceContextComponents(AppConfiguration appConfig,
		                                                    RemoteServiceConfiguration appRemoteServiceConfiguration,
		                                                    SpringBeansXMLBuilder builder) {
			this.configureClientRequestSenderComponents(appRemoteServiceConfiguration, builder);
		}

		protected void configureClientRequestSenderComponents(RemoteServiceConfiguration appRemoteServiceConfiguration,
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

}
