package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.iterate.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.model.crud.taskqueue.ModelKeyListAccessorDeleteTask;
import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.ListAccessorTaskRequestSenderImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.utility.TaskQueueIterateRequestBuilderUtility;

/**
 * Creates an iterate task configuration and scheduler task for deleting models
 * of a certain type. The scheduler builds iterate task requests based on the
 * parent model being input, and schedules all query matches to be deleted.
 *
 * @author dereekb
 *
 */
public class DeleteByParentIterateTaskConfigurer extends AbstractIterateConfigurerInstanceTaskEntryImpl {

	private static final String TASK_NAME_FORMAT = "DeleteBy%s";
	private static final String TASK_KEY_FORMAT = "deleteBy%s";

	private String parentType;
	private Class<?> taskRequestBuilderClass;

	private boolean hasFilter;

	public DeleteByParentIterateTaskConfigurer(String parentType, Class<?> taskRequestBuilderClass) {
		this(parentType, String.format(TASK_NAME_FORMAT, parentType), String.format(TASK_KEY_FORMAT, parentType),
		        taskRequestBuilderClass);
	}

	public DeleteByParentIterateTaskConfigurer(String parentType,
	        String taskName,
	        String taskKey,
	        Class<?> taskRequestBuilderClass) {
		super(taskName, taskKey);
		this.setParentType(parentType);
		this.setTaskRequestBuilderClass(taskRequestBuilderClass);
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		if (parentType == null) {
			throw new IllegalArgumentException("parentType cannot be null.");
		}

		this.parentType = parentType;
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

	public void setHasFilter(boolean hasFilter) {
		this.hasFilter = hasFilter;
	}

	// MARK: AbstractIterateConfigurerInstanceTaskEntryImpl
	@Override
	protected void configureTaskBeans(AppConfiguration appConfig,
	                                  AppModelConfiguration modelConfig,
	                                  SpringBeansXMLBuilder builder) {

		builder.comment("Task");
		builder.bean(this.getTaskBeanId(modelConfig)).beanClass(ModelKeyListAccessorDeleteTask.class).c()
		        .ref(modelConfig.getModelCrudServiceId());

		builder.comment("Scheduler");
		this.configureScheduleTask(modelConfig, builder);
	}

	protected void configureScheduleTask(AppModelConfiguration modelConfig,
	                                     SpringBeansXMLBuilder builder) {
		this.configureScheduleTask(modelConfig, builder, this.hasFilter);
	}

	protected void configureScheduleTask(AppModelConfiguration modelConfig,
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

	protected void configureFilteredScheduleTask(AppModelConfiguration modelConfig,
	                                             SpringBeansXMLBuilder builder,
	                                             String filteredSchedulingTaskBeanId,
	                                             String schedulingTaskBeanId) {
		throw new UnsupportedOperationException("Override in sub class.");
	}

	protected void configureScheduleTaskBuilder(AppModelConfiguration modelConfig,
	                                            SpringBeansXMLBuilder builder,
	                                            String taskBuilderBeanId) {

		String requestBeanId = taskBuilderBeanId + "Request";

		builder.bean(taskBuilderBeanId).beanClass(this.taskRequestBuilderClass).c().ref(requestBeanId);

		builder.bean(requestBeanId).beanClass(TaskQueueIterateRequestBuilderUtility.class)
		        .factoryMethod("makeIterateRequest").c().ref(this.getTaskKeyBeanId(modelConfig))
		        .ref(modelConfig.getModelTypeBeanId());
	}

}
