package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.client.api.server.schedule.impl.ClientScheduleTaskServiceRequestSenderImpl;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.event.webhook.listener.impl.WebHookEventSubmitterDelegateImpl;
import com.dereekb.gae.server.event.webhook.listener.impl.WebHookEventSubmitterImpl;

/**
 * {@link AppWebHookEventServiceConfigurer} implementation for services that use
 * a remote event service.
 *
 * @author dereekb
 *
 */
public class RemoteAppWebHookEventServiceConfigurerImpl
        implements AppWebHookEventServiceConfigurer {

	private AppRemoteServiceConfiguration eventServiceConfiguration;

	public RemoteAppWebHookEventServiceConfigurerImpl(AppRemoteServiceConfiguration eventServiceConfiguration) {
		this.setEventServiceConfiguration(eventServiceConfiguration);
	}

	public AppRemoteServiceConfiguration getEventServiceConfiguration() {
		return this.eventServiceConfiguration;
	}

	public void setEventServiceConfiguration(AppRemoteServiceConfiguration eventServiceConfiguration) {
		if (eventServiceConfiguration == null) {
			throw new IllegalArgumentException("eventServiceConfiguration cannot be null.");
		}

		this.eventServiceConfiguration = eventServiceConfiguration;
	}

	// MARK: AppWebHookEventServiceConfigurer
	@Override
	public void configureWebHookEventSubmitter(AppConfiguration appConfig,
	                                           SpringBeansXMLBuilder builder) {

		String webHookEventConverterBeanId = appConfig.getAppBeans().getWebHookEventConverterBeanId();

		String webHookEventSubmitterBeanId = appConfig.getAppBeans().getWebHookEventSubmitterBeanId();
		String webHookEventSubmitterDelegateBeanId = "webHookEventSubmitterDelegate";

		builder.bean(webHookEventSubmitterBeanId).beanClass(WebHookEventSubmitterImpl.class).c()
		        .ref(webHookEventConverterBeanId).ref(webHookEventSubmitterDelegateBeanId);

		String eventClientScheduleTaskServiceBeanId = "eventClientScheduleTaskService";
		builder.bean(webHookEventSubmitterDelegateBeanId).beanClass(WebHookEventSubmitterDelegateImpl.class).c()
		        .ref(appConfig.getAppBeans().getTaskSchedulerId()).ref(eventClientScheduleTaskServiceBeanId);

		builder.bean(eventClientScheduleTaskServiceBeanId).beanClass(ClientScheduleTaskServiceRequestSenderImpl.class)
		        .c().ref(this.getEventServiceConfiguration().getServiceBeansConfiguration()
		                .getClientApiRequestSenderBeanId());

	}

}
