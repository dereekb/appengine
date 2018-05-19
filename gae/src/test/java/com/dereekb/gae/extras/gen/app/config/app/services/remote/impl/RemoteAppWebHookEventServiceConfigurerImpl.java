package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
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

	private RemoteServiceConfiguration eventServiceConfiguration;
	private String eventClientScheduleTaskServiceBeanId = "eventClientScheduleTaskService";

	public RemoteAppWebHookEventServiceConfigurerImpl(RemoteServiceConfiguration eventServiceConfiguration) {
		this.setEventServiceConfiguration(eventServiceConfiguration);
	}

	public RemoteServiceConfiguration getEventServiceConfiguration() {
		return this.eventServiceConfiguration;
	}

	public void setEventServiceConfiguration(RemoteServiceConfiguration eventServiceConfiguration) {
		if (eventServiceConfiguration == null) {
			throw new IllegalArgumentException("eventServiceConfiguration cannot be null.");
		}

		this.eventServiceConfiguration = eventServiceConfiguration;
	}

	public String getEventClientScheduleTaskServiceBeanId() {
		return this.eventClientScheduleTaskServiceBeanId;
	}

	public void setEventClientScheduleTaskServiceBeanId(String eventClientScheduleTaskServiceBeanId) {
		if (eventClientScheduleTaskServiceBeanId == null) {
			throw new IllegalArgumentException("eventClientScheduleTaskServiceBeanId cannot be null.");
		}

		this.eventClientScheduleTaskServiceBeanId = eventClientScheduleTaskServiceBeanId;
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

		builder.bean(webHookEventSubmitterDelegateBeanId).beanClass(WebHookEventSubmitterDelegateImpl.class).c()
		        .ref(appConfig.getAppBeans().getTaskSchedulerId()).ref(this.eventClientScheduleTaskServiceBeanId);

		// Make as an alias of the schedule task bean.
		String eventServiceScheduleBean = this.eventServiceConfiguration.getServiceBeansConfiguration()
		        .getClientScheduleTaskServiceBeanId();
		builder.alias(eventServiceScheduleBean, this.eventClientScheduleTaskServiceBeanId);
	}

}
