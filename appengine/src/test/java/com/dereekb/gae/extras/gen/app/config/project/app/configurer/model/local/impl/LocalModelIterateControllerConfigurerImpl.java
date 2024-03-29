package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.impl.AbstractLocalModelBuilderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelIterateControllerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.iterate.IterateConfigurerInstanceTaskEntry;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.server.event.model.shared.event.impl.CommonModelEventType;
import com.dereekb.gae.web.taskqueue.model.crud.task.TaskQueueDeleteModelTask;
import com.dereekb.gae.web.taskqueue.model.crud.task.TaskQueueModelTask;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.impl.TaskQueueIterateControllerEntryImpl;

/**
 * {@link LocalModelIterateControllerConfigurerImpl} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelIterateControllerConfigurerImpl
        implements LocalModelIterateControllerConfigurer {

	// MARK: CustomLocalModelIterateControllerConfigurer
	@Override
	public boolean hasIterateControllerEntry() {
		return true;
	}

	@Override
	public void configureIterateControllerTasks(AppConfiguration appConfig,
	                                            LocalModelConfiguration modelConfig,
	                                            SpringBeansXMLBuilder builder) {
		this.makeInstance(appConfig, modelConfig, builder).configure();
	}

	protected ConfigurerInstance makeInstance(AppConfiguration appConfig,
	                                          LocalModelConfiguration modelConfig,
	                                          SpringBeansXMLBuilder builder) {
		return new IterateConfigurerInstance(appConfig, modelConfig, builder);
	}

	// MARK: IterateConfigurerInstance
	protected class IterateConfigurerInstance extends AbstractLocalModelBuilderConfigurerImpl {

		private boolean addEventTaskForCrud = true;

		private String postCreateKey = "create";
		private String postUpdateKey = "update";
		private String processDeleteKey = "delete";

		private transient List<IterateConfigurerInstanceTaskEntry> taskEntries;

		public IterateConfigurerInstance(AppConfiguration appConfig,
		        LocalModelConfiguration modelConfig,
		        SpringBeansXMLBuilder builder) {
			super(appConfig, modelConfig, builder);
		}

		public boolean isAddEventTaskForCrud() {
			return this.addEventTaskForCrud;
		}

		public void setAddEventTaskForCrud(boolean addEventTaskForCrud) {
			this.addEventTaskForCrud = addEventTaskForCrud;
		}

		public String getPostCreateKey() {
			return this.postCreateKey;
		}

		public void setPostCreateKey(String postCreateKey) {
			if (postCreateKey == null) {
				throw new IllegalArgumentException("postCreateKey cannot be null.");
			}

			this.postCreateKey = postCreateKey;
		}

		public String getPostUpdateKey() {
			return this.postUpdateKey;
		}

		public void setPostUpdateKey(String postUpdateKey) {
			if (postUpdateKey == null) {
				throw new IllegalArgumentException("postUpdateKey cannot be null.");
			}

			this.postUpdateKey = postUpdateKey;
		}

		public String getProcessDeleteKey() {
			return this.processDeleteKey;
		}

		public void setProcessDeleteKey(String processDeleteKey) {
			if (processDeleteKey == null) {
				throw new IllegalArgumentException("processDeleteKey cannot be null.");
			}

			this.processDeleteKey = processDeleteKey;
		}

		// MARK: AbstractBuilderConfigurerImpl
		@Override
		public void configure() {
			this.configureTaskControllerEntry();
			this.configureCrudTasks();
			this.configureIterateTasks();
		}

		protected void configureTaskControllerEntry() {
			this.builder.comment("Iterate Controller Entry");
			String taskQueueIterateControllerEntryId = this.getModelConfig().getModelBeanPrefix()
			        + "TaskQueueIterateControllerEntry";

			SpringBeansXMLMapBuilder<?> map = this.builder.bean(taskQueueIterateControllerEntryId)
			        .beanClass(TaskQueueIterateControllerEntryImpl.class).c().ref(this.getModelConfig().getModelRegistryId())
			        .map();
			this.configureTaskControllerEntryMap(map);
		}

		protected void configureTaskControllerEntryMap(SpringBeansXMLMapBuilder<?> map) {

			String postCreateTaskBeanId = this.getModelConfig().getModelBeanPrefix() + "TaskQueuePostCreateTask";
			String postUpdateTaskBeanId = this.getModelConfig().getModelBeanPrefix() + "TaskQueuePostUpdateTask";
			String processDeleteTaskBeanId = this.getModelConfig().getModelBeanPrefix() + "TaskQueueProcessDeleteTask";

			map.getRawXMLBuilder().comment("CRUD");

			map.entry(this.postCreateKey).valueRef(postCreateTaskBeanId);
			map.entry(this.postUpdateKey).valueRef(postUpdateTaskBeanId);
			map.entry(this.processDeleteKey).valueRef(processDeleteTaskBeanId);

			map.getRawXMLBuilder().comment("TASKS");

			for (IterateConfigurerInstanceTaskEntry entry : this.getTaskEntries()) {
				entry.configureMapEntry(this.getModelConfig(), map);
			}
		}

		protected void configureCrudTasks() {
			this.builder.comment("Iterate Controller CRUD Tasks");

			this.configurePostCreateTask();
			this.configurePostUpdateTask();
			this.configureProcessDeleteTask();
		}

		protected void configurePostCreateTask() {
			SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder = this.createPostCreateTask();
			this.configurePostCreateTaskList(builder.c().list());
		}

		protected void configurePostUpdateTask() {
			SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder = this.createPostUpdateTask();
			this.configurePostUpdateTaskList(builder.c().list());
		}

		protected void configureProcessDeleteTask() {
			SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder = this.createProcessDeleteTask();
			this.configureProcessDeleteTaskList(builder.c().list());
		}

		protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> createPostCreateTask() {
			return this.builder.bean(this.getModelConfig().getModelBeanPrefix() + "TaskQueuePostCreateTask")
			        .beanClass(TaskQueueModelTask.class);
		}

		protected void configurePostCreateTaskList(SpringBeansXMLListBuilder<?> taskListBuilder) {
			if (this.addEventTaskForCrud) {
				taskListBuilder.bean().factoryBean(this.getModelConfig().getModelEventServiceEntryBeanId())
				        .factoryMethod("makeSubmitEventTask").c().enumBean(CommonModelEventType.CREATED);
			}
		}

		protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> createPostUpdateTask() {
			return this.builder.bean(this.getModelConfig().getModelBeanPrefix() + "TaskQueuePostUpdateTask")
			        .beanClass(TaskQueueModelTask.class);
		}

		protected void configurePostUpdateTaskList(SpringBeansXMLListBuilder<?> taskListBuilder) {
			if (this.addEventTaskForCrud) {
				taskListBuilder.bean().factoryBean(this.getModelConfig().getModelEventServiceEntryBeanId())
				        .factoryMethod("makeSubmitEventTask").c().enumBean(CommonModelEventType.UPDATED);
			}
		}

		protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> createProcessDeleteTask() {
			return this.builder.bean(this.getModelConfig().getModelBeanPrefix() + "TaskQueueProcessDeleteTask")
			        .beanClass(TaskQueueDeleteModelTask.class).c().ref(this.getModelConfig().getModelRegistryId()).up();
		}

		protected void configureProcessDeleteTaskList(SpringBeansXMLListBuilder<?> taskListBuilder) {
			if (this.addEventTaskForCrud) {
				taskListBuilder.bean().factoryBean(this.getModelConfig().getModelEventServiceEntryBeanId())
				        .factoryMethod("makeSubmitEventTask").c().enumBean(CommonModelEventType.DELETED);
			}
		}

		// Iterate Tasks
		protected void configureIterateTasks() {
			this.builder.comment("Iterate Controller Tasks");

			for (IterateConfigurerInstanceTaskEntry entry : this.getTaskEntries()) {
				entry.configureTaskComponents(this.appConfig, this.getModelConfig(), this.builder);
			}
		}

		// MARK: Internal
		protected final List<IterateConfigurerInstanceTaskEntry> getTaskEntries() {
			if (this.taskEntries == null) {
				this.taskEntries = this.buildTaskEntries();
			}

			return this.taskEntries;
		}

		protected List<IterateConfigurerInstanceTaskEntry> buildTaskEntries() {
			return Collections.emptyList();
		}

	}

}
