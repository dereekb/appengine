package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationServiceRequestSenderImpl;
import com.dereekb.gae.client.api.auth.token.impl.service.RemoteAppLoginTokenDecoder;
import com.dereekb.gae.client.api.service.sender.security.impl.NoneSecuredClientApiRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.token.model.impl.SignatureConfigurationImpl;
import com.dereekb.gae.utilities.factory.impl.SingletonFactory;

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

	public RemoteAppLoginTokenSecurityConfigurerImpl(RemoteServiceConfiguration remoteLoginService) {
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

	// MARK: AppLoginTokenSecurityConfigurer
	@Override
	public void configureLoginTokenSecurityServiceComponents(AppConfiguration appConfig,
	                                                         SpringBeansXMLBuilder builder) {

		AppSecurityBeansConfigurer appSecurityBeansConfigurer = appConfig.getAppSecurityBeansConfigurer();

		String clientLoginTokenValidationServiceBeanId = appSecurityBeansConfigurer.getClientLoginTokenValidationServiceBeanId();

		builder.comment("Remote Login Service");
		builder.comment("Login Token Signature");
		String loginTokenSignatureFactoryId = appSecurityBeansConfigurer.getLoginTokenSignatureFactoryBeanId();

		builder.bean(loginTokenSignatureFactoryId).beanClass(SingletonFactory.class).c().bean()
		        .beanClass(SignatureConfigurationImpl.class).factoryMethod("defaultSignature");

		builder.comment("Login Token Validation Service");
		builder.bean(clientLoginTokenValidationServiceBeanId)
		        .beanClass(ClientLoginTokenValidationServiceRequestSenderImpl.class).c()
				.bean().beanClass(NoneSecuredClientApiRequestSenderImpl.class).c().ref(this.remoteLoginService.getServiceBeansConfiguration().getClientApiRequestSenderBeanId());

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
