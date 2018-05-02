package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateControllerEntry;

/**
 * Configurer for the {@link TaskQueueIterateControllerEntry} for a type.
 *
 * @author dereekb
 *
 */
public interface CustomLocalModelIterateControllerConfigurer {

	public boolean hasIterateControllerEntry();

	public void configureIterateControllerTasks(AppConfiguration appConfig,
	                                            AppModelConfiguration modelConfig,
	                                            SpringBeansXMLBuilder builder);

}
