package com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.app.config.model.configurer.impl.AbstractBuilderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelIterateControllerConfigurer;
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
		return new Instance(appConfig, modelConfig, builder);
	}

	// MARK: Instance
	protected class Instance extends AbstractBuilderConfigurerImpl {

		private String postCreateKey = "create";
		private String postUpdateKey = "update";
		private String processDeleteKey = "delete";

		private transient List<InstanceTaskEntry> taskEntries;

		public Instance(AppConfiguration appConfig, AppModelConfiguration modelConfig, SpringBeansXMLBuilder builder) {
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

			for (InstanceTaskEntry entry : this.getTaskEntries()) {
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

			for (InstanceTaskEntry entry : this.getTaskEntries()) {
				entry.configureTaskComponents(this.appConfig, this.modelConfig, this.builder);
			}
		}

		// MARK: Internal
		protected final List<InstanceTaskEntry> getTaskEntries() {
			if (this.taskEntries == null) {
				this.taskEntries = this.buildTaskEntries();
			}

			return this.taskEntries;
		}

		protected List<InstanceTaskEntry> buildTaskEntries() {
			return Collections.emptyList();
		}

	}

	// MARK: Instance Task Enttry
	protected interface InstanceTaskEntry {

		/**
		 * Adds this entry to the reference map.
		 */
		public void configureMapEntry(AppModelConfiguration modelConfig,
		                              SpringBeansXMLMapBuilder<?> map);

		/**
		 * Configures all components for this task.
		 */
		public void configureTaskComponents(AppConfiguration appConfig,
		                                    AppModelConfiguration modelConfig,
		                                    SpringBeansXMLBuilder builder);

	}

	protected abstract class InstanceTaskEntryImpl
	        implements InstanceTaskEntry {

		private boolean addModelPrefix = true;

		private String taskName;
		private String taskKey;

		public InstanceTaskEntryImpl(String taskName, String taskKey) {
			super();
			this.setTaskKey(taskKey);
			this.setTaskName(taskName);
		}

		public String getTaskName() {
			return this.taskName;
		}

		public void setTaskName(String taskName) {
			if (taskName == null) {
				throw new IllegalArgumentException("taskName cannot be null.");
			}

			this.taskName = taskName;
		}

		public String getTaskKey() {
			return this.taskKey;
		}

		public void setTaskKey(String taskKey) {
			if (taskKey == null) {
				throw new IllegalArgumentException("taskKey cannot be null.");
			}

			this.taskKey = taskKey;
		}

		// MARK: InstanceTaskEntry
		@Override
		public void configureMapEntry(AppModelConfiguration modelConfig,
		                              SpringBeansXMLMapBuilder<?> map) {
			map.entry(this.getTaskKeyBeanId(modelConfig), true).keyRef(this.getTaskBeanId(modelConfig));
		}

		@Override
		public final void configureTaskComponents(AppConfiguration appConfig,
		                                          AppModelConfiguration modelConfig,
		                                          SpringBeansXMLBuilder builder) {
			builder.comment(this.getTaskName() + " Task");
			this.configureTaskKey(appConfig, modelConfig, builder);
			this.configureTaskBeans(appConfig, modelConfig, builder);
		}

		protected void configureTaskKey(AppConfiguration appConfig,
		                                AppModelConfiguration modelConfig,
		                                SpringBeansXMLBuilder builder) {
			builder.stringBean(this.getTaskKeyBeanId(modelConfig), this.taskKey);
		}

		protected abstract void configureTaskBeans(AppConfiguration appConfig,
		                                           AppModelConfiguration modelConfig,
		                                           SpringBeansXMLBuilder builder);

		// MARK: Internal
		protected String getTaskKeyBeanId(AppModelConfiguration modelConfig) {
			return this.getTaskBeanId(modelConfig) + "Key";
		}

		protected String getTaskBeanId(AppModelConfiguration modelConfig) {
			String prefix = (this.addModelPrefix) ? modelConfig.getModelBeanPrefix() : "";
			return prefix + this.taskName + "Task";
		}

	}

}
