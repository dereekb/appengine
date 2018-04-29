package com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.app.config.model.configurer.impl.AbstractBuilderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelIterateControllerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.iterate.IterateConfigurerInstanceTaskEntry;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.web.taskqueue.model.crud.task.TaskQueueDeleteModelTask;
import com.dereekb.gae.web.taskqueue.model.crud.task.TaskQueueModelTask;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.impl.TaskQueueIterateControllerEntryImpl;

/**
 * {@link CustomLocalModelIterateControllerConfigurerImpl} implementation.
 *
 * @author dereekb
 *
 */
public class CustomLocalModelIterateControllerConfigurerImpl
        implements CustomLocalModelIterateControllerConfigurer {

	// MARK: CustomLocalModelIterateControllerConfigurer
	@Override
	public boolean hasIterateControllerEntry() {
		return true;
	}

	@Override
	public void configureIterateControllerTasks(AppConfiguration appConfig,
	                                            AppModelConfiguration modelConfig,
	                                            SpringBeansXMLBuilder builder) {
		this.makeInstance(appConfig, modelConfig, builder).configure();
	}

	protected ConfigurerInstance makeInstance(AppConfiguration appConfig,
	                                          AppModelConfiguration modelConfig,
	                                          SpringBeansXMLBuilder builder) {
		return new IterateConfigurerInstance(appConfig, modelConfig, builder);
	}

	// MARK: IterateConfigurerInstance
	protected class IterateConfigurerInstance extends AbstractBuilderConfigurerImpl {

		private String postCreateKey = "create";
		private String postUpdateKey = "update";
		private String processDeleteKey = "delete";

		private transient List<IterateConfigurerInstanceTaskEntry> taskEntries;

		public IterateConfigurerInstance(AppConfiguration appConfig, AppModelConfiguration modelConfig, SpringBeansXMLBuilder builder) {
			super(appConfig, modelConfig, builder);
		}

		@Override
		public void configure() {
			this.configureTaskControllerEntry();
			this.configureCrudTasks();
			this.configureIterateTasks();
		}

		protected void configureTaskControllerEntry() {
			this.builder.comment("Iterate Controller Entry");
			String taskQueueIterateControllerEntryId = this.modelConfig.getModelBeanPrefix()
			        + "TaskQueueIterateControllerEntry";

			SpringBeansXMLMapBuilder<?> map = this.builder.bean(taskQueueIterateControllerEntryId)
			        .beanClass(TaskQueueIterateControllerEntryImpl.class).c().ref(this.modelConfig.getModelRegistryId())
			        .map();
			this.configureTaskControllerEntryMap(map);
		}

		protected void configureTaskControllerEntryMap(SpringBeansXMLMapBuilder<?> map) {

			String postCreateTaskBeanId = this.modelConfig.getModelBeanPrefix() + "TaskQueuePostCreateTask";
			String postUpdateTaskBeanId = this.modelConfig.getModelBeanPrefix() + "TaskQueuePostUpdateTask";
			String processDeleteTaskBeanId = this.modelConfig.getModelBeanPrefix() + "TaskQueueProcessDeleteTask";

			map.getRawXMLBuilder().comment("CRUD");

			map.entry(this.postCreateKey).valueRef(postCreateTaskBeanId);
			map.entry(this.postUpdateKey).valueRef(postUpdateTaskBeanId);
			map.entry(this.processDeleteKey).valueRef(processDeleteTaskBeanId);

			map.getRawXMLBuilder().comment("TASKS");

			for (IterateConfigurerInstanceTaskEntry entry : this.getTaskEntries()) {
				entry.configureMapEntry(this.modelConfig, map);
			}
		}

		protected void configureCrudTasks() {
			this.builder.comment("Iterate Controller CRUD Tasks");

			this.configurePostCreateTask();
			this.configurePostUpdateTask();
			this.configureProcessDeleteTask();
		}

		protected void configurePostCreateTask() {
			this.createPostCreateTask();
		}

		protected void configurePostUpdateTask() {
			this.createPostUpdateTask();
		}

		protected void configureProcessDeleteTask() {
			this.createProcessDeleteTask();
		}

		protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> createPostCreateTask() {
			return this.builder.bean(this.modelConfig.getModelBeanPrefix() + "TaskQueuePostCreateTask")
			        .beanClass(TaskQueueModelTask.class);
		}

		protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> createPostUpdateTask() {
			return this.builder.bean(this.modelConfig.getModelBeanPrefix() + "TaskQueuePostUpdateTask")
			        .beanClass(TaskQueueModelTask.class);
		}

		protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> createProcessDeleteTask() {
			return this.builder.bean(this.modelConfig.getModelBeanPrefix() + "TaskQueueProcessDeleteTask")
			        .beanClass(TaskQueueDeleteModelTask.class).c().ref(this.modelConfig.getModelRegistryId()).up();
		}

		// Iterate Tasks
		protected void configureIterateTasks() {
			this.builder.comment("Iterate Controller Tasks");

			for (IterateConfigurerInstanceTaskEntry entry : this.getTaskEntries()) {
				entry.configureTaskComponents(this.appConfig, this.modelConfig, this.builder);
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
