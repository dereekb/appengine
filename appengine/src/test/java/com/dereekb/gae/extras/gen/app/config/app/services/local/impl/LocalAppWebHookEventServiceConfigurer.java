package com.dereekb.gae.extras.gen.app.config.app.services.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.event.webhook.listener.impl.NoopWebHookEventSubmitter;

/**
 * {@link AppWebHookEventServiceConfigurer} implementation for the EventService
 * microservice.
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

		// TODO: This will only be used by the EventService service. Update with proper webhook submissions.

		String webHookEventSubmitterBeanId = appConfig.getAppBeans().getWebHookEventSubmitterBeanId();
		builder.bean(webHookEventSubmitterBeanId).beanClass(NoopWebHookEventSubmitter.class);
	}

}
