package com.dereekb.gae.extras.gen.app.config.app.services.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.SystemLoginTokenFactoryConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.app.service.impl.AppConfiguredAppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecurityDetailsServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecurityServiceImpl;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.system.impl.SystemLoginTokenFactoryImpl;
import com.dereekb.gae.server.auth.security.system.impl.SystemLoginTokenServiceImpl;

/**
 * {@link SystemLoginTokenFactoryConfigurer} implementation for a system that
 * verifies its own login tokens.
 *
 * @author dereekb
 *
 */
public class LocalSystemLoginTokenFactoryConfigurerImpl
        implements SystemLoginTokenFactoryConfigurer {

	// MARK: SystemLoginTokenFactoryConfigurer
	@Override
	public void configureSystemLoginTokenFactory(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder builder) {

		if (appConfig.isLoginServer() == false) {
			throw new UnsupportedOperationException();
		}

		builder.comment("Login Token System");
		String systemEncodedRolesId = "systemEncodedRoles";

		String systemLoginTokenServiceBeanId = appConfig.getAppBeans().getSystemLoginTokenServiceBeanId();

		builder.bean(systemLoginTokenServiceBeanId).beanClass(SystemLoginTokenServiceImpl.class).c()
		        .ref(systemEncodedRolesId).ref(appConfig.getAppBeans().getLoginTokenServiceBeanId());

		builder.bean(appConfig.getAppBeans().getSystemLoginTokenFactoryBeanId())
		        .beanClass(SystemLoginTokenFactoryImpl.class).c().ref(systemLoginTokenServiceBeanId)
		        .ref(appConfig.getAppBeans().getAppLoginSecuritySigningServiceBeanId());

		Long adminEncodedRole = 1L;
		builder.longBean(systemEncodedRolesId, adminEncodedRole);

		builder.comment("App Security");
		String appLoginSecurityDetailsServiceId = "appLoginSecurityDetailsService";
		String appLoginSecurityVerifierServiceId = appConfig.getAppBeans().getAppLoginSecurityVerifierServiceBeanId();
		String appLoginSecuritySigningServiceId = appConfig.getAppBeans().getAppLoginSecuritySigningServiceBeanId();

		builder.bean(appConfig.getAppBeans().getAppLoginSecurityServiceBeanId())
		        .beanClass(AppLoginSecurityServiceImpl.class).c().bean()
		        .beanClass(AppLoginSecuritySigningServiceImpl.class).up().ref(appLoginSecurityDetailsServiceId);

		builder.alias(appConfig.getAppBeans().getAppLoginSecurityServiceBeanId(), appLoginSecurityVerifierServiceId);

		builder.bean(appLoginSecurityDetailsServiceId).beanClass(AppLoginSecurityDetailsServiceImpl.class).c()
		        .ref("appRegistry");

		builder.bean(appLoginSecuritySigningServiceId).beanClass(AppConfiguredAppLoginSecuritySigningServiceImpl.class)
		        .c().ref(appConfig.getAppBeans().getAppInfoBeanId())
		        .ref(appConfig.getAppBeans().getAppLoginSecurityServiceBeanId());

	}

}
