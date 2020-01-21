package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.iterate.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.ListAccessorTaskRequestSenderImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.utility.TaskQueueIterateRequestBuilderUtility;

/**
 * Abstract configurer that creates an iterate task configuration and scheduler
 * task for a specific task configured on the subclass.
 *
 * @author dereekb
 *
 */
public abstract class AbstractScheduleIterateTaskConfigurer extends AbstractIterateConfigurerInstanceTaskEntryImpl {

	private Class<?> taskRequestBuilderClass;

	private boolean hasFilter;

	public AbstractScheduleIterateTaskConfigurer(String taskName, Class<?> taskRequestBuilderClass) {
		super(taskName);
		this.setTaskRequestBuilderClass(taskRequestBuilderClass);
	}

	public Class<?> getTaskRequestBuilderClass() {
		return this.taskRequestBuilderClass;
	}

	public void setTaskRequestBuilderClass(Class<?> taskRequestBuilderClass) {
		if (taskRequestBuilderClass == null) {
			throw new IllegalArgumentException("taskRequestBuilderClass cannot be null.");
		}

		this.taskRequestBuilderClass = taskRequestBuilderClass;
	}

	public boolean hasFilter() {
		return this.hasFilter;
	}

	public void setHasFilter() {
		this.setHasFilter(true);
	}

	public void setHasFilter(boolean hasFilter) {
		this.hasFilter = hasFilter;
	}

	// MARK: AbstractIterateConfigurerInstanceTaskEntryImpl
	@Override
	protected void configureTaskBeans(AppConfiguration appConfig,
	                                  LocalModelConfiguration modelConfig,
	                                  SpringBeansXMLBuilder builder) {

		builder.comment("Task");
		String taskBeanId = this.getTaskBeanId(modelConfig);
		this.configureModelKeyListAccessorTaskBean(appConfig, modelConfig, builder, taskBeanId);

		builder.comment("Scheduler");
		this.configureScheduleTask(modelConfig, builder);
	}

	protected abstract void configureModelKeyListAccessorTaskBean(AppConfiguration appConfig,
	                                                              LocalModelConfiguration modelConfig,
	                                                              SpringBeansXMLBuilder builder,
	                                                              String taskBeanId);

	protected void configureScheduleTask(LocalModelConfiguration modelConfig,
	                                     SpringBeansXMLBuilder builder) {
		this.configureScheduleTask(modelConfig, builder, this.hasFilter);
	}

	protected void configureScheduleTask(LocalModelConfiguration modelConfig,
	                                     SpringBeansXMLBuilder builder,
	                                     boolean hasFilter) {
		String filteredSchedulingTaskBeanId = "schedule"
		        + StringUtility.firstLetterUpperCase(this.getTaskBeanId(modelConfig));
		String schedulingTaskBeanId = filteredSchedulingTaskBeanId + "Scheduler";

		if (hasFilter) {
			this.configureFilteredScheduleTask(modelConfig, builder, filteredSchedulingTaskBeanId,
			        schedulingTaskBeanId);
		} else {
			schedulingTaskBeanId = filteredSchedulingTaskBeanId;
		}

		// Post-Filter Scheduling Components
		String taskBuilderBeanId = filteredSchedulingTaskBeanId + "Builder";

		builder.bean(schedulingTaskBeanId).beanClass(ListAccessorTaskRequestSenderImpl.class).c().ref(taskBuilderBeanId)
		        .ref("taskScheduler");

		this.configureScheduleTaskBuilder(modelConfig, builder, taskBuilderBeanId);
	}

	protected void configureFilteredScheduleTask(LocalModelConfiguration modelConfig,
	                                             SpringBeansXMLBuilder builder,
	                                             String filteredSchedulingTaskBeanId,
	                                             String schedulingTaskBeanId) {
		throw new UnsupportedOperationException("Override in sub class.");
	}

	protected void configureScheduleTaskBuilder(LocalModelConfiguration modelConfig,
	                                            SpringBeansXMLBuilder builder,
	                                            String taskBuilderBeanId) {

		String requestBeanId = taskBuilderBeanId + "Request";

		builder.bean(taskBuilderBeanId).beanClass(this.taskRequestBuilderClass).c().ref(requestBeanId);

		builder.bean(requestBeanId).beanClass(TaskQueueIterateRequestBuilderUtility.class)
		        .factoryMethod("makeIterateRequest").c().ref(this.getTaskKeyBeanId(modelConfig))
		        .ref(modelConfig.getModelTypeBeanId());
	}

}
