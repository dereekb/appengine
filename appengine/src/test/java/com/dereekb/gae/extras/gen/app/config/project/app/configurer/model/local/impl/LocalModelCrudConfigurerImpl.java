package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import java.lang.reflect.Constructor;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.impl.AbstractLocalModelBuilderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelCrudConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.model.crud.services.components.impl.CreateServiceImpl;
import com.dereekb.gae.model.crud.services.components.impl.DeleteServiceImpl;
import com.dereekb.gae.model.crud.services.components.impl.ReadServiceImpl;
import com.dereekb.gae.model.crud.services.components.impl.SafeUpdateServiceImpl;
import com.dereekb.gae.model.crud.services.impl.CrudServiceImpl;
import com.dereekb.gae.model.crud.task.impl.CreateTaskImpl;
import com.dereekb.gae.model.crud.task.impl.ValidatedCreateTaskImpl;
import com.dereekb.gae.model.crud.task.impl.delegate.impl.CreateTaskDelegateImpl;
import com.dereekb.gae.model.crud.task.impl.delete.ScheduleDeleteTask;
import com.dereekb.gae.model.crud.task.impl.filtered.FilteredReadTaskImpl;
import com.dereekb.gae.model.crud.task.impl.filtered.FilteredUpdateTaskImpl;
import com.jamesmurty.utils.XMLBuilder2;

/**
 * {@link LocalModelCrudConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelCrudConfigurerImpl
        implements LocalModelCrudConfigurer {

	@Override
	public void configureCrudServiceComponents(AppConfiguration appConfig,
	                                           LocalModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder) {
		new LocalModelCrudConfigurerInstance(appConfig, modelConfig, builder).configure();
	}

	public static class LocalModelCrudConfigurerInstance extends AbstractLocalModelBuilderConfigurerImpl {

		private boolean filterWithModelConfig = true;
		private boolean addTodoWarnings = false;
		private boolean assertTodoOverridden = true;

		public LocalModelCrudConfigurerInstance(AppConfiguration appConfig,
		        LocalModelConfiguration modelConfig,
		        SpringBeansXMLBuilder builder) {
			super(appConfig, modelConfig, builder);
		}

		// MARK: AbstractBuilderConfigurer
		@Override
		public void configure() {

			if (!this.modelConfig.isInternalModelOnly()) {
				if (this.modelConfig.getCrudsConfiguration().hasCrudService()) {
					this.configureCrudServiceBean();
					this.configureCrudServicesBeans();
				}
			}

			this.configureUtilityBeans();
		}

		protected void configureCrudServiceBean() {
			SpringBeansXMLBeanConstructorBuilder<?> crudsConstructor = this.makeCrudServiceBean().c();

			if (this.getModelConfig().hasCreateService()) {
				crudsConstructor.ref(this.getModelConfig().getModelCreateServiceId());
			} else {
				crudsConstructor.nullArg();
			}

			crudsConstructor.ref(this.getModelConfig().getModelReadServiceId());

			if (this.getModelConfig().hasUpdateService()) {
				crudsConstructor.ref(this.getModelConfig().getModelUpdateServiceId());
			} else {
				crudsConstructor.nullArg();
			}

			if (this.getModelConfig().hasDeleteService()) {
				crudsConstructor.ref(this.getModelConfig().getModelDeleteServiceId());
			} else {
				crudsConstructor.nullArg();
			}

		}

		protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> makeCrudServiceBean() {
			return this.builder.bean(this.getModelConfig().getModelCrudServiceId()).beanClass(CrudServiceImpl.class);
		}

		// MARK: CRUD Services
		protected void configureCrudServicesBeans() {
			boolean hasCreateService = this.getModelConfig().hasCreateService();

			if (hasCreateService || this.filterWithModelConfig == false) {
				this.builder.comment("Create Service");
				this.configureCreateService();
			}

			this.builder.comment("Read Service");
			this.configureReadService();

			boolean hasUpdateService = this.getModelConfig().hasUpdateService();

			if (hasUpdateService || this.filterWithModelConfig == false) {
				this.builder.comment("Update Service");
				this.configureUpdateService();
			}

			if (hasCreateService || hasUpdateService) {
				this.makeAttributeUpdater();
			}

			if (this.getModelConfig().hasDeleteService() || this.filterWithModelConfig == false) {
				this.builder.comment("Delete Service");
				this.configureDeleteService();
			}
		}

		protected void configureCreateService() {
			String modelBeanPrefix = this.getModelConfig().getModelBeanPrefix();

			String createTask = modelBeanPrefix + "CreateTask";
			this.builder.bean(this.getModelConfig().getModelCreateServiceId()).beanClass(CreateServiceImpl.class).c()
			        .ref(createTask);

			this.builder.comment("Create Task");
			String attributeUpdater = this.getAttributeUpdaterBeanId();
			String createAttributeUpdater = modelBeanPrefix + "CreateAttributeUpdater";

			String createTaskDelegate = modelBeanPrefix + "CreateTaskDelegate";
			String storerTask = this.getModelConfig().getModelSetterTaskBeanId();

			boolean isValidated = this.getModelConfig().hasValidatedCreate();

			Class<?> createClass = (isValidated) ? ValidatedCreateTaskImpl.class : CreateTaskImpl.class;
			SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> createBuilder = this.builder.bean(createTask)
			        .beanClass(createClass);
			SpringBeansXMLBeanConstructorBuilder<?> createConstructor = createBuilder.c().ref(createTaskDelegate)
			        .ref(storerTask).ref(this.getModelConfig().getModelScheduleCreateReviewBeanId());

			if (isValidated) {
				this.configureValidatedCreateTask(createConstructor);
			}

			this.builder.bean(createTaskDelegate).beanClass(CreateTaskDelegateImpl.class).c()
			        .ref(this.getModelConfig().getNewModelFactoryBeanId()).ref(createAttributeUpdater);

			if (this.getModelConfig().hasCreateAttributeUpdater()) {
				this.makeCreateAttributeUpdater(createAttributeUpdater, attributeUpdater);
			} else {
				this.builder.alias(attributeUpdater, createAttributeUpdater);
			}
		}

		protected void configureValidatedCreateTask(SpringBeansXMLBeanConstructorBuilder<?> builder) {
			// Override in super-class
			if (this.addTodoWarnings || this.assertTodoOverridden) {
				this.writeTodoCommentForBuilder(builder.nextArgBuilder(), "Add validation bean info.");
			}
		}

		protected void makeCreateAttributeUpdater(String createAttributeUpdaterBeanId,
		                                          String attributeUpdaterBeanId) {
			SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder = this.builder.bean(createAttributeUpdaterBeanId)
			        .beanClass(this.getModelConfig().getModelCreateAttributeUpdaterClass());
			this.configureCreateAttributeUpdater(builder, attributeUpdaterBeanId);
		}

		protected SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder>> configureCreateAttributeUpdater(SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder,
		                                                                                                                                 String attributeUpdaterBeanId) {
			return builder.c().ref(attributeUpdaterBeanId);
		}

		// MARK: Read
		protected void configureReadService() {
			String readTask = this.getModelConfig().getModelBeanPrefix() + "ReadTask";

			this.builder.bean(this.getModelConfig().getModelReadServiceId()).beanClass(ReadServiceImpl.class).c()
			        .ref(readTask);

			this.builder.comment("Read Task");
			this.builder.bean(readTask).beanClass(FilteredReadTaskImpl.class).c()
			        .ref(this.getModelConfig().getModelRegistryId()).bean()
			        .factoryBean(this.getModelConfig().getModelSecurityContextServiceEntryBeanId())
			        .factoryMethod("makeRoleFilter").c()
			        .ref(this.getAppConfig().getAppBeans().getCrudReadModelRoleRefBeanId());
		}

		protected void configureUpdateService() {
			String modelBeanPrefix = this.getModelConfig().getModelBeanPrefix();

			String updateTask = modelBeanPrefix + "UpdateTask";
			String attributeUpdaterBeanId = this.getAttributeUpdaterBeanId();

			this.builder.bean(this.getModelConfig().getModelUpdateServiceId()).beanClass(SafeUpdateServiceImpl.class)
			        .c().ref(this.getModelConfig().getModelReadServiceId()).ref(updateTask)
			        .ref(this.getModelConfig().getModelScheduleUpdateReviewBeanId());

			this.builder.comment("Update Task");
			String updaterTask = this.getModelConfig().getModelSetterTaskBeanId();

			this.builder.bean(this.getModelConfig().getModelBeanPrefix() + "UpdateTask")
			        .beanClass(FilteredUpdateTaskImpl.class).c().ref(attributeUpdaterBeanId).ref(updaterTask).bean()
			        .factoryBean(this.getModelConfig().getModelSecurityContextServiceEntryBeanId())
			        .factoryMethod("makeRoleFilter").c()
			        .ref(this.getAppConfig().getAppBeans().getCrudUpdateModelRoleRefBeanId());
		}

		protected void configureDeleteService() {
			String deleteTask = this.getModelConfig().getModelBeanPrefix() + "DeleteTask";

			this.builder.bean(this.getModelConfig().getModelDeleteServiceId()).beanClass(DeleteServiceImpl.class).c()
			        .ref(this.getModelConfig().getModelReadServiceId()).ref(deleteTask);

			this.builder.comment("Delete Task");
			String scheduleDeleteTask = this.getModelConfig().getModelBeanPrefix() + "ScheduleDeleteTask";

			this.builder.alias(scheduleDeleteTask, this.getModelConfig().getModelBeanPrefix() + "DeleteTask");

			this.builder.bean(scheduleDeleteTask).beanClass(ScheduleDeleteTask.class).c()
			        .ref(this.getModelConfig().getModelTypeBeanId())
			        .ref(this.getAppConfig().getAppBeans().getTaskSchedulerId()).up().property("deleteFilter").bean()
			        .factoryBean(this.getModelConfig().getModelSecurityContextServiceEntryBeanId())
			        .factoryMethod("makeRoleFilter").c()
			        .ref(this.getAppConfig().getAppBeans().getCrudDeleteModelRoleRefBeanId());
		}

		protected String getAttributeUpdaterBeanId() {
			return this.getModelConfig().getModelBeanPrefix() + "AttributeUpdater";
		}

		protected void makeAttributeUpdater() {
			String attributeUpdaterBeanId = this.getAttributeUpdaterBeanId();
			this.makeAttributeUpdater(attributeUpdaterBeanId);
		}

		protected void makeAttributeUpdater(String attributeUpdaterBeanId) {
			SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder = this.builder.bean(attributeUpdaterBeanId)
			        .beanClass(this.getModelConfig().getModelAttributeUpdaterClass());
			this.configureAttributeUpdater(builder, attributeUpdaterBeanId);
		}

		protected void configureAttributeUpdater(SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder,
		                                         String attributeUpdaterBeanId) {

			// Write warning if there is a constructor with multiple arguments.
			Constructor<?>[] constructors = this.getModelConfig().getModelAttributeUpdaterClass().getConstructors();

			if (constructors.length > 0) {
				for (int i = 0; i < constructors.length; i += 1) {
					if (constructors[i].getParameterTypes().length > 0) {
						this.writeTodoCommentForBuilder(builder.getRawXMLBuilder(),
						        "Add constructor args if applicable.");
						return;
					}
				}
			}
		}

		protected void configureUtilityBeans() {

			// Utility
			if (this.getModelConfig().hasRelatedAttributeUtility()) {
				this.builder.bean(this.getModelConfig().getModelBeanPrefix() + "RelatedModelAttributeUtility")
				        .beanClass(this.getModelConfig().getModelRelatedModelAttributeUtilityClass()).c()
				        .ref(this.getModelConfig().getModelSecurityContextServiceEntryBeanId());
			}

		}

		// MARK: Internal
		protected void writeTodoCommentForBuilder(XMLBuilder2 builder,
		                                          String message) {
			if (this.addTodoWarnings) {
				builder.c("TODO: " + message);
			} else if (this.assertTodoOverridden) {
				throw new RuntimeException(
				        "Override CRUDs configurer for type: " + this.getModelConfig().getModelType());
			}
		}

	}

}
