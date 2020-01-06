package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.iterate.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.model.crud.taskqueue.ModelKeyListAccessorDeleteTask;

/**
 * Creates an iterate task configuration and scheduler task for deleting models
 * of a certain type. The scheduler builds iterate task requests based on the
 * parent model being input, and schedules all query matches to be deleted.
 *
 * @author dereekb
 *
 */
public class DeleteByParentIterateTaskConfigurer extends AbstractScheduleIterateTaskConfigurer {

	private static final String TASK_NAME_AND_KEY_FORMAT = "DeleteBy%s";

	private String parentType;

	public DeleteByParentIterateTaskConfigurer(String parentType, Class<?> taskRequestBuilderClass) {
		this(parentType, String.format(TASK_NAME_AND_KEY_FORMAT, parentType), taskRequestBuilderClass);
	}

	public DeleteByParentIterateTaskConfigurer(String parentType, String taskName, Class<?> taskRequestBuilderClass) {
		super(taskName, taskRequestBuilderClass);
		this.setParentType(parentType);
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

	// MARK: AbstractScheduleIterateTaskConfigurer
	@Override
	protected void configureModelKeyListAccessorTaskBean(AppConfiguration appConfig,
	                                                     LocalModelConfiguration modelConfig,
	                                                     SpringBeansXMLBuilder builder,
	                                                     String taskBeanId) {
		builder.bean(taskBeanId).beanClass(ModelKeyListAccessorDeleteTask.class).c()
		        .ref(modelConfig.getModelCrudServiceId());
	}

}
