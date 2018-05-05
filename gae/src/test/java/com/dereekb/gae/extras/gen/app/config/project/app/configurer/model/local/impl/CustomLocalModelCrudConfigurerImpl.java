package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import java.lang.reflect.Constructor;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.impl.AbstractModelBuilderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelCrudConfigurer;
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
 * {@link CustomLocalModelCrudConfigurer}
 *
 * @author dereekb
 *
 */
public class CustomLocalModelCrudConfigurerImpl
        implements CustomLocalModelCrudConfigurer {

	@Override
	public void configureCrudServiceComponents(AppConfiguration appConfig,
	                                           LocalModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder) {
		new CrudConfigurerInstance(appConfig, modelConfig, builder).configure();
	}

	public static class CrudConfigurerInstance extends AbstractModelBuilderConfigurerImpl {

		private boolean filterWithModelConfig = true;
		private boolean addTodoWarnings = false;
		private boolean assertTodoOverridden = true;

		public CrudConfigurerInstance(AppConfiguration appConfig,
		        LocalModelConfiguration modelConfig,
		        SpringBeansXMLBuilder builder) {
			super(appConfig, modelConfig, builder);
		}

		// MARK: AbstractBuilderConfigurer
		@Override
		public void configure() {
			this.configureCrudServiceBean();
			this.configureCrudServicesBeans();
			this.configureUtilityBeans();
		}

		protected void configureCrudServiceBean() {
			SpringBeansXMLBeanConstructorBuilder<?> crudsConstructor = this.makeCrudServiceBean().c();

			if (this.modelConfig.hasCreateService()) {
				crudsConstructor.ref(this.modelConfig.getModelCreateServiceId());
			} else {
				crudsConstructor.nullArg();
			}

			crudsConstructor.ref(this.modelConfig.getModelReadServiceId());

			if (this.modelConfig.hasUpdateService()) {
				crudsConstructor.ref(this.modelConfig.getModelUpdateServiceId());
			} else {
				crudsConstructor.nullArg();
			}

			if (this.modelConfig.hasDeleteService()) {
				crudsConstructor.ref(this.modelConfig.getModelDeleteServiceId());
			} else {
				crudsConstructor.nullArg();
			}

		}

		protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> makeCrudServiceBean() {
			return this.builder.bean(this.modelConfig.getModelCrudServiceId()).beanClass(CrudServiceImpl.class);
		}

		// MARK: CRUD Services
		protected void configureCrudServicesBeans() {
			if (this.modelConfig.hasCreateService() || this.filterWithModelConfig == false) {
				this.builder.comment("Create Service");
				this.configureCreateService();
			}

			this.builder.comment("Read Service");
			this.configureReadService();

			if (this.modelConfig.hasUpdateService() || this.filterWithModelConfig == false) {
				this.builder.comment("Update Service");
				this.configureUpdateService();
			}

			if (this.modelConfig.hasDeleteService() || this.filterWithModelConfig == false) {
				this.builder.comment("Delete Service");
				this.configureDeleteService();
			}
		}

		protected void configureCreateService() {
			String modelBeanPrefix = this.modelConfig.getModelBeanPrefix();

			String createTask = modelBeanPrefix + "CreateTask";
			this.builder.bean(this.modelConfig.getModelCreateServiceId()).beanClass(CreateServiceImpl.class).c()
			        .ref(createTask);

			this.builder.comment("Create Task");
			String attributeUpdater = modelBeanPrefix + "AttributeUpdater";
			String createAttributeUpdater = modelBeanPrefix + "CreateAttributeUpdater";

			String createTaskDelegate = modelBeanPrefix + "CreateTaskDelegate";
			String storerTask = this.modelConfig.getModelSetterTaskBeanId();

			boolean isValidated = this.modelConfig.hasValidatedCreate();

			Class<?> createClass = (isValidated) ? ValidatedCreateTaskImpl.class : CreateTaskImpl.class;
			SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> createBuilder = this.builder.bean(createTask)
			        .beanClass(createClass);
			SpringBeansXMLBeanConstructorBuilder<?> createConstructor = createBuilder.c().ref(createTaskDelegate)
			        .ref(storerTask).ref(this.modelConfig.getModelScheduleCreateReviewBeanId());

			if (isValidated) {
				this.configureValidatedCreateTask(createConstructor);
			}

			this.builder.bean(createTaskDelegate).beanClass(CreateTaskDelegateImpl.class).c()
			        .ref(this.modelConfig.getNewModelFactoryBeanId()).ref(createAttributeUpdater);

			if (this.modelConfig.hasCreateAttributeUpdater()) {
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
			        .beanClass(this.modelConfig.getModelCreateAttributeUpdaterClass());
			this.configureCreateAttributeUpdater(builder, attributeUpdaterBeanId);
		}

		protected SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder>> configureCreateAttributeUpdater(SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder,
		                                                                                                                                 String attributeUpdaterBeanId) {
			return builder.c().ref(attributeUpdaterBeanId);
		}

		// MARK: Read
		protected void configureReadService() {
			String readTask = this.modelConfig.getModelBeanPrefix() + "ReadTask";

			this.builder.bean(this.modelConfig.getModelReadServiceId()).beanClass(ReadServiceImpl.class).c()
			        .ref(readTask);

			this.builder.comment("Read Task");
			this.builder.bean(readTask).beanClass(FilteredReadTaskImpl.class).c()
			        .ref(this.modelConfig.getModelRegistryId()).bean()
			        .factoryBean(this.modelConfig.getModelSecurityContextServiceEntryBeanId())
			        .factoryMethod("makeRoleFilter").c()
			        .ref(this.getAppConfig().getAppBeans().getCrudReadModelRoleRefBeanId());
		}

		protected void configureUpdateService() {
			String modelBeanPrefix = this.modelConfig.getModelBeanPrefix();

			String updateTask = modelBeanPrefix + "UpdateTask";
			String attributeUpdater = modelBeanPrefix + "AttributeUpdater";

			this.builder.bean(this.modelConfig.getModelUpdateServiceId()).beanClass(SafeUpdateServiceImpl.class).c()
			        .ref(this.modelConfig.getModelReadServiceId()).ref(updateTask)
			        .ref(this.modelConfig.getModelScheduleUpdateReviewBeanId());

			this.builder.comment("Update Task");
			String updaterTask = this.modelConfig.getModelSetterTaskBeanId();

			this.builder.bean(this.modelConfig.getModelBeanPrefix() + "UpdateTask")
			        .beanClass(FilteredUpdateTaskImpl.class).c().ref(attributeUpdater).ref(updaterTask).bean()
			        .factoryBean(this.modelConfig.getModelSecurityContextServiceEntryBeanId())
			        .factoryMethod("makeRoleFilter").c()
			        .ref(this.getAppConfig().getAppBeans().getCrudUpdateModelRoleRefBeanId());

			this.makeAttributeUpdater(attributeUpdater);
		}

		protected void configureDeleteService() {
			String deleteTask = this.modelConfig.getModelBeanPrefix() + "DeleteTask";

			this.builder.bean(this.modelConfig.getModelDeleteServiceId()).beanClass(DeleteServiceImpl.class).c()
			        .ref(this.modelConfig.getModelReadServiceId()).ref(deleteTask);

			this.builder.comment("Delete Task");
			String scheduleDeleteTask = this.modelConfig.getModelBeanPrefix() + "ScheduleDeleteTask";

			this.builder.alias(scheduleDeleteTask, this.modelConfig.getModelBeanPrefix() + "DeleteTask");

			this.builder.bean(scheduleDeleteTask).beanClass(ScheduleDeleteTask.class).c()
			        .ref(this.modelConfig.getModelTypeBeanId())
			        .ref(this.getAppConfig().getAppBeans().getTaskSchedulerId()).up().property("deleteFilter").bean()
			        .factoryBean(this.modelConfig.getModelSecurityContextServiceEntryBeanId())
			        .factoryMethod("makeRoleFilter").c()
			        .ref(this.getAppConfig().getAppBeans().getCrudDeleteModelRoleRefBeanId());
		}

		protected void makeAttributeUpdater(String attributeUpdaterBeanId) {
			SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder = this.builder.bean(attributeUpdaterBeanId)
			        .beanClass(this.modelConfig.getModelAttributeUpdaterClass());
			this.configureAttributeUpdater(builder, attributeUpdaterBeanId);
		}

		protected void configureAttributeUpdater(SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder,
		                                         String attributeUpdaterBeanId) {

			// Write warning if there is a constructor with multiple arguments.
			Constructor<?>[] constructors = this.modelConfig.getModelAttributeUpdaterClass().getConstructors();

			if (constructors.length > 0) {
				for (int i = 0; i < constructors.length; i += 1) {
					if (constructors[i].getParameterTypes().length > 0) {
						this.writeTodoCommentForBuilder(builder.getRawXMLBuilder(), "Add constructor args if applicable.");
						return;
					}
				}
			}
		}

		protected void configureUtilityBeans() {

			// Utility
			if (this.modelConfig.hasRelatedAttributeUtility()) {
				this.builder.bean(this.modelConfig.getModelBeanPrefix() + "RelatedModelAttributeUtility")
				        .beanClass(this.modelConfig.getModelRelatedModelAttributeUtilityClass()).c()
				        .ref(this.modelConfig.getModelSecurityContextServiceEntryBeanId());
			}

		}

		// MARK: Internal
		protected void writeTodoCommentForBuilder(XMLBuilder2 builder,
		                                          String message) {
			if (this.addTodoWarnings) {
				builder.c("TODO: " + message);
			} else if (this.assertTodoOverridden) {
				throw new RuntimeException("Override CRUDs configurer for type: " + this.modelConfig.getModelType());
			}
		}

	}

}
