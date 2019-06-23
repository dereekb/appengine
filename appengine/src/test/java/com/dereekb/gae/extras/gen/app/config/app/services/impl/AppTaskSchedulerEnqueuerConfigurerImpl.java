package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppTaskSchedulerEnqueuerConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.AppEngineTaskSchedulerEnqueuer;
import com.dereekb.gae.server.taskqueue.scheduler.appengine.utility.converter.impl.AppEngineTaskRequestConverterImpl;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;

/**
 * {@link AppTaskSchedulerEnqueuerConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class AppTaskSchedulerEnqueuerConfigurerImpl
        implements AppTaskSchedulerEnqueuerConfigurer {

	@Override
	public void configureTaskSchedulerEnqueuerComponents(AppConfiguration appConfig,
	                                                     SpringBeansXMLBuilder builder) {

		String taskSchedulerEnqueuerFactoryBeanId = "taskEnqueuerFactory";

		String devTaskSchedulerEnqueuerBeanId = "devTaskEnqueuer";
		String prodTaskSchedulerEnqueuerBeanId = "prodTaskEnqueuer";

		// Dev - Send Requests to Server Host
		builder.bean(devTaskSchedulerEnqueuerBeanId).beanClass(AppEngineTaskSchedulerEnqueuer.class).property("queue")
		        .ref(appConfig.getAppBeans().getTaskQueueNameId()).up().c().bean()
		        .beanClass(AppEngineTaskRequestConverterImpl.class).property("hostTarget")
		        .value(appConfig.getAppDevelopmentServerHostUrl());

		// Prod
		builder.bean(prodTaskSchedulerEnqueuerBeanId).beanClass(AppEngineTaskSchedulerEnqueuer.class).property("queue")
		        .ref(appConfig.getAppBeans().getTaskQueueNameId());

		builder.bean(taskSchedulerEnqueuerFactoryBeanId).beanClass(GoogleAppEngineContextualFactoryImpl.class)
		        .property("developmentSingleton").ref(devTaskSchedulerEnqueuerBeanId).up()
		        .property("defaultSingleton").ref(prodTaskSchedulerEnqueuerBeanId);

		// Enqueuer
		builder.bean(appConfig.getAppBeans().getTaskSchedulerEnqueurerBeanId())
		        .factoryBean(taskSchedulerEnqueuerFactoryBeanId).factoryMethod("make");

	}

}
