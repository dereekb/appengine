package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.client.api.auth.system.impl.ClientSystemLoginTokenServiceRequestSenderImpl;
import com.dereekb.gae.client.api.service.sender.security.impl.NoneSecuredClientApiRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.SystemLoginTokenFactoryConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.app.impl.AppLoginSecurityDetailsImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.PreConfiguredAppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.system.impl.SystemLoginTokenFactoryImpl;
import com.dereekb.gae.utilities.data.StringUtility;

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
		        .bean().beanClass(NoneSecuredClientApiRequestSenderImpl.class).c()
		        .ref(this.remoteLoginService.getServiceBeansConfiguration().getClientApiRequestSenderBeanId()).up().up()
		        .ref(appConfig.getAppBeans().getLoginTokenDecoderBeanId());

		builder.bean(appConfig.getAppBeans().getSystemLoginTokenFactoryBeanId())
		        .beanClass(SystemLoginTokenFactoryImpl.class).c().ref(systemLoginTokenServiceBeanId)
		        .ref(appConfig.getAppBeans().getAppLoginSecuritySigningServiceBeanId());

		builder.comment("App Security");
		String appLoginSecurityDetailsBeanId = "appLoginSecurityDetails";

		builder.bean(appLoginSecurityDetailsBeanId).beanClass(AppLoginSecurityDetailsImpl.class).c()
		        .ref(appConfig.getAppBeans().getAppInfoBeanId()).ref(appConfig.getAppBeans().getAppSecretBeanId());

		String appLoginSecuritySigningServiceId = appConfig.getAppBeans().getAppLoginSecuritySigningServiceBeanId();

		String appSecret = appConfig.getAppSecret();

		if (StringUtility.isEmptyString(appSecret)) {
			throw new RuntimeException("Remote app requires an app secret to be set.");
		}

		builder.bean(appLoginSecuritySigningServiceId).beanClass(PreConfiguredAppLoginSecuritySigningServiceImpl.class)
		        .c().ref(appLoginSecurityDetailsBeanId);

		// TODO: Add other parts!

	}

}
