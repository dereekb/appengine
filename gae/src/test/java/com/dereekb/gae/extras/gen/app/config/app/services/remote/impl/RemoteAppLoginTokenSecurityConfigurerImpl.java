package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationServiceRequestSenderImpl;
import com.dereekb.gae.client.api.auth.token.impl.service.RemoteAppLoginTokenDecoder;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppLoginTokenSecurityConfigurer} implementation for a remote login
 * service.
 *
 * @author dereekb
 *
 */
public class RemoteAppLoginTokenSecurityConfigurerImpl
        implements AppLoginTokenSecurityConfigurer {

	private RemoteServiceConfiguration remoteLoginService;

	public RemoteServiceConfiguration getRemoteLoginService() {
		return this.remoteLoginService;
	}

	public void setRemoteLoginService(RemoteServiceConfiguration remoteLoginService) {
		if (remoteLoginService == null) {
			throw new IllegalArgumentException("remoteLoginService cannot be null.");
		}

		this.remoteLoginService = remoteLoginService;
	}

	// MARK: AppLoginTokenSecurityConfigurer
	@Override
	public void configureLoginTokenSecurityServiceComponents(AppConfiguration appConfig,
	                                                         SpringBeansXMLBuilder builder) {

		AppSecurityBeansConfigurer appSecurityBeansConfigurer = appConfig.getAppSecurityBeansConfigurer();

		String clientLoginTokenValidationServiceBeanId = "clientLoginTokenValidationService";

		builder.comment("Login Token Validation Service");
		builder.bean(clientLoginTokenValidationServiceBeanId)
		        .beanClass(ClientLoginTokenValidationServiceRequestSenderImpl.class).c()
		        .ref(this.remoteLoginService.getServiceBeansConfiguration().getSecuredClientApiRequestSenderBeanId());

		builder.comment("Login Token Decoder");
		SpringBeansXMLBeanConstructorBuilder<?> decoderBuilder = builder
		        .bean(appConfig.getAppBeans().getLoginTokenDecoderBeanId()).beanClass(RemoteAppLoginTokenDecoder.class)
		        .c();

		// Configure the dencoder
		SpringBeansXMLBeanBuilder<?> dencoderBean = decoderBuilder.bean();

		appConfig.getAppSecurityBeansConfigurer().configureTokenEncoderDecoder(appConfig, dencoderBean);
		decoderBuilder.ref(clientLoginTokenValidationServiceBeanId);


		SpringBeansXMLBeanBuilder<?> loginTokenBuilderBuilder = builder.bean("loginTokenBuilder");
		appSecurityBeansConfigurer.configureTokenBuilder(appConfig, loginTokenBuilderBuilder);


		// TODO: For Testing there is only the decoder component.
		// TODO: For Prod, send requests to the prod login service.

	}

}
