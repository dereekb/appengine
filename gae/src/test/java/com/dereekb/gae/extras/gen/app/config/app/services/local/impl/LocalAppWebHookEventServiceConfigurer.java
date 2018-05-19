package com.dereekb.gae.extras.gen.app.config.app.services.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppWebHookEventServiceConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class LocalAppWebHookEventServiceConfigurer
        implements AppWebHookEventServiceConfigurer {

	// MARK: AppWebHookEventServiceConfigurer
	@Override
	public void configureWebHookEventSubmitter(AppConfiguration appConfig,
	                                           SpringBeansXMLBuilder builder) {

		// TODO: Do nothing?

		/*
		String webHookEventConverterBeanId = appConfig.getAppBeans().getWebHookEventConverterBeanId();

		String webHookEventSubmitterBeanId = appConfig.getAppBeans().getWebHookEventSubmitterBeanId();
		String webHookEventSubmitterDelegatåeBeanId = "webHookEventSubmitterDelegate";
		*/
	}

}
