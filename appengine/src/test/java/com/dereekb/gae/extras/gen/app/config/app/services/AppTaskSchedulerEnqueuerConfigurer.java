package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.taskqueue.scheduler.TaskSchedulerEnqueuer;

/**
 * Used for configuring the {@link TaskSchedulerEnqueuer} for the application.
 *
 * @author dereekb
 *
 */
public interface AppTaskSchedulerEnqueuerConfigurer {

	/**
	 * Configures the task scheduler Enqueuer and related components.
	 *
	 * @param appConfig
	 * @param builder
	 */
	public void configureTaskSchedulerEnqueuerComponents(AppConfiguration appConfig,
	                                                     SpringBeansXMLBuilder builder);

}
