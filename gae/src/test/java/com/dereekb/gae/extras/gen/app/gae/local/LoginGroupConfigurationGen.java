package com.dereekb.gae.extras.gen.app.gae.local;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelCrudsConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.AdminOnlySecuredQueryInitializerConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomLocalModelContextConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomLocalModelCrudConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomLocalModelIterateControllerConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomLocalModelRoleSetLoaderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.SecurityModelQueryInitializerConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.iterate.IterateConfigurerInstanceTaskEntry;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.iterate.impl.DeleteByParentIterateTaskConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLListBuilder;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.key.taskqueue.LoginKeyLoginPointerQueryTaskRequestBuilder;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.model.pointer.link.LoginPointerLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.pointer.misc.LoginPointerTypeFilter;
import com.dereekb.gae.server.auth.model.pointer.taskqueue.LoginPointerLoginQueryTaskRequestBuilder;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.ModelKeyListAccessorTaskFilter;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * Local model configuration for {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginGroupConfigurationGen {

	public static LocalModelConfigurationGroupImpl makeLocalLoginGroupConfig() {

		// Login
		LocalModelConfigurationImpl loginModel = makeLoginModelConfig();
		LocalModelConfigurationImpl loginPointerModel = makeLoginPointerModelConfig();
		LocalModelConfigurationImpl loginKeyModel = makeLoginKeyModelConfig();

		LocalModelConfigurationGroupImpl loginGroup = new LocalModelConfigurationGroupImpl("login",
		        ListUtility.toList(loginModel, loginPointerModel, loginKeyModel));
		return loginGroup;
	}

	public static LocalModelConfigurationImpl makeLoginModelConfig() {
		LocalModelConfigurationImpl loginModel = new LocalModelConfigurationImpl(Login.class);

		LocalModelCrudsConfigurationImpl crudsConfiguration = new LocalModelCrudsConfigurationImpl(loginModel);
		crudsConfiguration.setHasCreateService(false);
		loginModel.setCrudsConfiguration(crudsConfiguration);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer
		        .setCustomLocalModelIterateControllerConfigurer(new LoginIterateControllerConfigurer());
		customLocalModelContextConfigurer
		        .setSecuredQueryInitializerConfigurer(new AdminOnlySecuredQueryInitializerConfigurerImpl());

		loginModel.setCustomModelContextConfigurer(customLocalModelContextConfigurer);

		return loginModel;
	}

	public static LocalModelConfigurationImpl makeLoginPointerModelConfig() {
		LocalModelConfigurationImpl loginPointerModel = new LocalModelConfigurationImpl(LoginPointer.class);

		LocalModelCrudsConfigurationImpl crudsConfiguration = new LocalModelCrudsConfigurationImpl(loginPointerModel);
		crudsConfiguration.setHasCreateService(false);
		loginPointerModel.setCrudsConfiguration(crudsConfiguration);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer
		        .setCustomLocalModelIterateControllerConfigurer(new LoginPointerIterateControllerConfigurer());
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("loginOwnedModelQuerySecurityDelegate"));
		customLocalModelContextConfigurer
		        .setCustomLocalModelRoleSetLoaderConfigurer(new LoginParentSecurityRoleConfigurer());

		loginPointerModel.setCustomModelContextConfigurer(customLocalModelContextConfigurer);

		return loginPointerModel;
	}

	public static LocalModelConfigurationImpl makeLoginKeyModelConfig() {
		LocalModelConfigurationImpl loginKeyModel = new LocalModelConfigurationImpl(LoginKey.class);

		// loginKeyModel.setHasCreateService(false);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer
		        .setCustomLocalModelIterateControllerConfigurer(new LoginKeyIterateControllerConfigurer());
		customLocalModelContextConfigurer.setCustomLocalModelCrudConfigurer(new LoginKeyCrudConfigurer());
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("loginOwnedModelQuerySecurityDelegate"));
		customLocalModelContextConfigurer
		        .setCustomLocalModelRoleSetLoaderConfigurer(new LoginParentSecurityRoleConfigurer());

		loginKeyModel.setCustomModelContextConfigurer(customLocalModelContextConfigurer);

		return loginKeyModel;
	}

	// MARK: Internal
	private static class LoginParentSecurityRoleConfigurer extends CustomLocalModelRoleSetLoaderConfigurerImpl {

		@Override
		public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
		                                                  LocalModelConfiguration modelConfig,
		                                                  SpringBeansXMLBuilder builder) {

			this.saferMakeRoleBuilderComponent(modelConfig, builder).c().ref("loginParentModelRoleSetContextReader");
			this.makeRoleSetLoaderComponent(modelConfig, builder, true);
		}

	}

	private static class LoginIterateControllerConfigurer extends CustomLocalModelIterateControllerConfigurerImpl {

		@Override
		protected ConfigurerInstance makeInstance(AppConfiguration appConfig,
		                                          LocalModelConfiguration modelConfig,
		                                          SpringBeansXMLBuilder builder) {
			return new LoginIterateConfigurerInstance(appConfig, modelConfig, builder);
		}

		protected class LoginIterateConfigurerInstance extends IterateConfigurerInstance {

			public LoginIterateConfigurerInstance(AppConfiguration appConfig,
			        LocalModelConfiguration modelConfig,
			        SpringBeansXMLBuilder builder) {
				super(appConfig, modelConfig, builder);
			}

			@Override
			protected void configureProcessDeleteTaskList(SpringBeansXMLListBuilder<?> taskListBuilder) {
				// Add Delete Children Task
				taskListBuilder.ref(
				        LoginPointerIterateControllerConfigurer.LoginPointerDeleteByParentIterateTaskConfigurer.SCHEDULE_TASK_NAME);

				// Add Default Tasks
				super.configureProcessDeleteTaskList(taskListBuilder);
			}

			@Override
			protected List<IterateConfigurerInstanceTaskEntry> buildTaskEntries() {
				List<IterateConfigurerInstanceTaskEntry> entries = new ArrayList<IterateConfigurerInstanceTaskEntry>();

				return entries;
			}

		}

	}

	public static class LoginPointerIterateControllerConfigurer extends CustomLocalModelIterateControllerConfigurerImpl {

		@Override
		protected ConfigurerInstance makeInstance(AppConfiguration appConfig,
		                                          LocalModelConfiguration modelConfig,
		                                          SpringBeansXMLBuilder builder) {
			return new LoginIterateConfigurerInstance(appConfig, modelConfig, builder);
		}

		protected class LoginIterateConfigurerInstance extends IterateConfigurerInstance {

			public LoginIterateConfigurerInstance(AppConfiguration appConfig,
			        LocalModelConfiguration modelConfig,
			        SpringBeansXMLBuilder builder) {
				super(appConfig, modelConfig, builder);
			}

			@Override
			protected void configureProcessDeleteTaskList(SpringBeansXMLListBuilder<?> taskListBuilder) {
				// Add Delete Children Task
				taskListBuilder.ref(
				        LoginKeyIterateControllerConfigurer.LoginKeyDeleteByParentIterateTaskConfigurer.SCHEDULE_TASK_NAME);

				// Add Default Tasks
				super.configureProcessDeleteTaskList(taskListBuilder);
			}

			@Override
			protected List<IterateConfigurerInstanceTaskEntry> buildTaskEntries() {
				List<IterateConfigurerInstanceTaskEntry> entries = new ArrayList<IterateConfigurerInstanceTaskEntry>();

				// Delete LoginPointers Belonging to Login
				entries.add(new LoginPointerDeleteByParentIterateTaskConfigurer());

				return entries;
			}

		}

		/**
		 * Configures a scheduler and task to delete LoginPointer values
		 * belonging to a parent Login that was deleted.
		 *
		 * @author dereekb
		 *
		 */
		public static class LoginPointerDeleteByParentIterateTaskConfigurer extends DeleteByParentIterateTaskConfigurer {

			private static final String SCHEDULE_TASK_NAME = "scheduleLoginPointerDeleteByLoginTask";

			private LoginPointerDeleteByParentIterateTaskConfigurer() {
				super(LoginLinkSystemBuilderEntry.LINK_MODEL_TYPE, LoginPointerLoginQueryTaskRequestBuilder.class);
			}

		}

	}

	public static class LoginKeyCrudConfigurer extends CustomLocalModelCrudConfigurerImpl {

		@Override
		public void configureCrudServiceComponents(AppConfiguration appConfig,
		                                           LocalModelConfiguration modelConfig,
		                                           SpringBeansXMLBuilder builder) {
			new LoginKeyCrudConfigurerInstance(appConfig, modelConfig, builder).configure();
		}

		protected class LoginKeyCrudConfigurerInstance extends CrudConfigurerInstance {

			public LoginKeyCrudConfigurerInstance(AppConfiguration appConfig,
			        LocalModelConfiguration modelConfig,
			        SpringBeansXMLBuilder builder) {
				super(appConfig, modelConfig, builder);
			}

			@Override
			protected SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder>> configureCreateAttributeUpdater(SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder,
			                                                                                                                                 String attributeUpdaterBeanId) {
				return super.configureCreateAttributeUpdater(builder, attributeUpdaterBeanId)
				        .ref("loginRelatedModelAttributeUtility");
			}

		}

	}

	public static class LoginKeyIterateControllerConfigurer extends CustomLocalModelIterateControllerConfigurerImpl {

		@Override
		protected ConfigurerInstance makeInstance(AppConfiguration appConfig,
		                                          LocalModelConfiguration modelConfig,
		                                          SpringBeansXMLBuilder builder) {
			return new LoginIterateConfigurerInstance(appConfig, modelConfig, builder);
		}

		protected class LoginIterateConfigurerInstance extends IterateConfigurerInstance {

			public LoginIterateConfigurerInstance(AppConfiguration appConfig,
			        LocalModelConfiguration modelConfig,
			        SpringBeansXMLBuilder builder) {
				super(appConfig, modelConfig, builder);
			}

			@Override
			protected List<IterateConfigurerInstanceTaskEntry> buildTaskEntries() {
				List<IterateConfigurerInstanceTaskEntry> entries = new ArrayList<IterateConfigurerInstanceTaskEntry>();

				// Delete LoginKeys Belonging to LoginPointer
				entries.add(new LoginKeyDeleteByParentIterateTaskConfigurer());

				return entries;
			}

		}

		/**
		 * Configures a scheduler and task to delete LoginKey values belonging
		 * to a parent LoginPointer that was deleted.
		 *
		 * @author dereekb
		 *
		 */
		public static class LoginKeyDeleteByParentIterateTaskConfigurer extends DeleteByParentIterateTaskConfigurer {

			private static final String SCHEDULE_TASK_NAME = "scheduleLoginKeyDeleteByLoginPointerTask";

			private LoginKeyDeleteByParentIterateTaskConfigurer() {
				super(LoginPointerLinkSystemBuilderEntry.LINK_MODEL_TYPE,
				        LoginKeyLoginPointerQueryTaskRequestBuilder.class);
				this.setHasFilter(true);
			}

			@Override
			protected void configureFilteredScheduleTask(LocalModelConfiguration modelConfig,
			                                             SpringBeansXMLBuilder builder,
			                                             String filteredSchedulingTaskBeanId,
			                                             String schedulingTaskBeanId) {

				String filterBean = "loginPointerIsApiKeyPointerFilter";

				builder.bean(filteredSchedulingTaskBeanId).beanClass(ModelKeyListAccessorTaskFilter.class).c()
				        .ref(filterBean).ref(schedulingTaskBeanId);

				builder.bean(filterBean).beanClass(LoginPointerTypeFilter.class).c()
				        .value(LoginPointerType.API_KEY.name());

			}

		}

	}

}
