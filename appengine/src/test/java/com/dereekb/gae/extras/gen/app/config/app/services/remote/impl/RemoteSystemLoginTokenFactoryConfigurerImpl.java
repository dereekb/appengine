package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.client.api.auth.system.impl.ClientSystemLoginTokenServiceRequestSenderImpl;
import com.dereekb.gae.client.api.auth.token.impl.ClientAppLoginSecurityVerifierServiceImpl;
import com.dereekb.gae.client.api.service.sender.security.impl.NoneSecuredClientApiRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.SystemLoginTokenFactoryConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.app.impl.AppLoginSecurityDetailsImpl;
import com.dereekb.gae.server.auth.security.app.impl.LocalQueryAppLoginSecurityDetailsImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.PreConfiguredAppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.system.impl.SystemLoginTokenFactoryImpl;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link SystemLoginTokenFactoryConfigurer} implementation for a system that
 * uses a remote service for verifying login tokens.
 *
 * @author dereekb
 *
 */
public class RemoteSystemLoginTokenFactoryConfigurerImpl
        implements SystemLoginTokenFactoryConfigurer {

	private boolean hasAppAvailableLocally;
	private RemoteServiceConfiguration remoteLoginService;

	public RemoteSystemLoginTokenFactoryConfigurerImpl(RemoteServiceConfiguration remoteLoginService) {
		this(remoteLoginService, false);
	}

	public RemoteSystemLoginTokenFactoryConfigurerImpl(RemoteServiceConfiguration remoteLoginService,
	        boolean hasAppAvailableLocally) {
		this.setRemoteLoginService(remoteLoginService);
		this.setHasAppAvailableLocally(hasAppAvailableLocally);
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

	public boolean hasAppAvailableLocally() {
		return this.hasAppAvailableLocally;
	}

	public void setHasAppAvailableLocally(boolean hasAppAvailableLocally) {
		this.hasAppAvailableLocally = hasAppAvailableLocally;
	}

	// MARK: SystemLoginTokenFactoryConfigurer
	@Override
	public void configureSystemLoginTokenFactory(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder builder) {

		builder.comment("Login Token System");
		String systemLoginTokenServiceBeanId = appConfig.getAppBeans().getSystemLoginTokenServiceBeanId();

		builder.bean(systemLoginTokenServiceBeanId).beanClass(ClientSystemLoginTokenServiceRequestSenderImpl.class).c()
		        .bean().beanClass(NoneSecuredClientApiRequestSenderImpl.class).c()
		        .ref(this.remoteLoginService.getServiceBeansConfiguration().getClientApiRequestSenderBeanId()).up().up()
		        .ref(appConfig.getAppBeans().getLoginTokenDecoderBeanId());

		builder.bean(appConfig.getAppBeans().getSystemLoginTokenFactoryBeanId())
		        .beanClass(SystemLoginTokenFactoryImpl.class).c().ref(systemLoginTokenServiceBeanId)
		        .ref(appConfig.getAppBeans().getAppLoginSecuritySigningServiceBeanId());

		builder.comment("App Security");
		String appLoginSecurityDetailsBeanId = "appLoginSecurityDetails";

		if (this.hasAppAvailableLocally) {
			builder.bean(appLoginSecurityDetailsBeanId).beanClass(LocalQueryAppLoginSecurityDetailsImpl.class).c()
			        .ref(appConfig.getAppBeans().getAppInfoBeanId()).ref("appRegistry");
		} else {
			builder.bean(appLoginSecurityDetailsBeanId).beanClass(AppLoginSecurityDetailsImpl.class).c()
			        .ref(appConfig.getAppBeans().getAppInfoBeanId()).ref(appConfig.getAppBeans().getAppSecretBeanId());
		}

		String appLoginSecurityVerifierServiceId = appConfig.getAppBeans().getAppLoginSecurityVerifierServiceBeanId();
		String appLoginSecuritySigningServiceId = appConfig.getAppBeans().getAppLoginSecuritySigningServiceBeanId();

		String appSecret = appConfig.getAppSecret();

		if (StringUtility.isEmptyString(appSecret)) {
			throw new RuntimeException("Remote app requires an app secret to be set.");
		}

		String clientLoginTokenValidationServiceBeanId = appConfig.getAppSecurityBeansConfigurer()
		        .getClientLoginTokenValidationServiceBeanId();

		builder.bean(appLoginSecurityVerifierServiceId).beanClass(ClientAppLoginSecurityVerifierServiceImpl.class).c()
		        .ref(clientLoginTokenValidationServiceBeanId);
		builder.bean(appLoginSecuritySigningServiceId).beanClass(PreConfiguredAppLoginSecuritySigningServiceImpl.class)
		        .c().ref(appLoginSecurityDetailsBeanId);

		// TODO: Add other parts!

	}

}
