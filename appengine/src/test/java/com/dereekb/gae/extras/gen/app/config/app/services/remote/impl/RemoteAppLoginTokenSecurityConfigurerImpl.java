package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationServiceRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.security.impl.NoneSecuredClientApiRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.factory.impl.NullFactoryImpl;

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
		builder.bean(loginTokenSignatureFactoryId).beanClass(NullFactoryImpl.class); // Signature factory is not needed on remote apps, but must still be configured.

		builder.comment("Login Token Validation Service");
		builder.bean(clientLoginTokenValidationServiceBeanId)
		        .beanClass(ClientLoginTokenValidationServiceRequestSenderImpl.class).c()
				.bean().beanClass(NoneSecuredClientApiRequestSenderImpl.class).c().ref(this.remoteLoginService.getServiceBeansConfiguration().getClientApiRequestSenderBeanId());

		builder.comment("Login Token Decoder");

		// Configure the decoder
		SpringBeansXMLBeanBuilder<?> decoderBean = builder.bean(appConfig.getAppBeans().getLoginTokenDecoderBeanId());
		appConfig.getAppSecurityBeansConfigurer().configureTokenEncoderDecoder(appConfig, decoderBean, false);

		// TODO: Unneeded by the remote server and can probably be removed.
		// SpringBeansXMLBeanBuilder<?> loginTokenBuilderBuilder = builder.bean("loginTokenBuilder");
		// appSecurityBeansConfigurer.configureTokenBuilder(appConfig, loginTokenBuilderBuilder);
	}

}
