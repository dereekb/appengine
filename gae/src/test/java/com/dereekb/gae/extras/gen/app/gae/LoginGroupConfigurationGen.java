package com.dereekb.gae.extras.gen.app.gae;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.AdminOnlySecuredQueryInitializerConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.CustomLocalModelContextConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.CustomLocalModelIterateControllerConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.CustomLocalModelRoleSetLoaderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.SecurityModelQueryInitializerConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.iterate.IterateConfigurerInstanceTaskEntry;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.iterate.impl.DeleteByParentIterateTaskConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
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

public class LoginGroupConfigurationGen {

	public static AppModelConfigurationGroupImpl makeLocalLoginGroupConfig() {

		// Login
		AppModelConfigurationImpl loginModel = makeLoginModelConfig();
		AppModelConfigurationImpl loginPointerModel = makeLoginPointerModelConfig();
		AppModelConfigurationImpl loginKeyModel = makeLoginKeyModelConfig();

		AppModelConfigurationGroupImpl loginGroup = new AppModelConfigurationGroupImpl("login",
		        ListUtility.toList(loginModel, loginPointerModel, loginKeyModel));
		return loginGroup;
	}

	public static AppModelConfigurationImpl makeLoginModelConfig() {
		AppModelConfigurationImpl loginModel = new AppModelConfigurationImpl(Login.class);

		loginModel.setHasCreateService(false);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer
		        .setCustomLocalModelIterateControllerConfigurer(new LoginIterateControllerConfigurer());
		customLocalModelContextConfigurer
		        .setSecuredQueryInitializerConfigurer(new AdminOnlySecuredQueryInitializerConfigurerImpl());

		loginModel.setCustomLocalModelContextConfigurer(customLocalModelContextConfigurer);

		return loginModel;
	}

	public static AppModelConfigurationImpl makeLoginPointerModelConfig() {
		AppModelConfigurationImpl loginPointerModel = new AppModelConfigurationImpl(LoginPointer.class);

		loginPointerModel.setHasCreateService(false);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer
		        .setCustomLocalModelIterateControllerConfigurer(new LoginPointerIterateControllerConfigurer());
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("loginOwnedModelQuerySecurityDelegate"));
		customLocalModelContextConfigurer
		        .setCustomLocalModelRoleSetLoaderConfigurer(new LoginParentSecurityRoleConfigurer());

		loginPointerModel.setCustomLocalModelContextConfigurer(customLocalModelContextConfigurer);

		return loginPointerModel;
	}

	public static AppModelConfigurationImpl makeLoginKeyModelConfig() {
		AppModelConfigurationImpl loginKeyModel = new AppModelConfigurationImpl(LoginKey.class);

		// loginKeyModel.setHasCreateService(false);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer
		        .setCustomLocalModelIterateControllerConfigurer(new LoginKeyIterateControllerConfigurer());
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("loginOwnedModelQuerySecurityDelegate"));
		customLocalModelContextConfigurer
		        .setCustomLocalModelRoleSetLoaderConfigurer(new LoginParentSecurityRoleConfigurer());

		loginKeyModel.setCustomLocalModelContextConfigurer(customLocalModelContextConfigurer);

		return loginKeyModel;
	}

	// MARK: Internal
	private static class LoginParentSecurityRoleConfigurer extends CustomLocalModelRoleSetLoaderConfigurerImpl {

		@Override
		public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
		                                                  AppModelConfiguration modelConfig,
		                                                  SpringBeansXMLBuilder builder) {

			this.saferMakeRoleBuilderComponent(modelConfig, builder).c().ref("loginParentModelRoleSetContextReader");
			this.makeRoleSetLoaderComponent(modelConfig, builder, true);
		}

	}

	private static class LoginIterateControllerConfigurer extends CustomLocalModelIterateControllerConfigurerImpl {

		@Override
		protected ConfigurerInstance makeInstance(AppConfiguration appConfig,
		                                          AppModelConfiguration modelConfig,
		                                          SpringBeansXMLBuilder builder) {
			return new LoginIterateConfigurerInstance(appConfig, modelConfig, builder);
		}

		protected class LoginIterateConfigurerInstance extends IterateConfigurerInstance {

			public LoginIterateConfigurerInstance(AppConfiguration appConfig,
			        AppModelConfiguration modelConfig,
			        SpringBeansXMLBuilder builder) {
				super(appConfig, modelConfig, builder);
			}

			@Override
			protected void configureProcessDeleteTask() {
				// Add Delete Children Task
				this.createProcessDeleteTask().c().list().ref(
				        LoginPointerIterateControllerConfigurer.LoginPointerDeleteByParentIterateTaskConfigurer.SCHEDULE_TASK_NAME);
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
		                                          AppModelConfiguration modelConfig,
		                                          SpringBeansXMLBuilder builder) {
			return new LoginIterateConfigurerInstance(appConfig, modelConfig, builder);
		}

		protected class LoginIterateConfigurerInstance extends IterateConfigurerInstance {

			public LoginIterateConfigurerInstance(AppConfiguration appConfig,
			        AppModelConfiguration modelConfig,
			        SpringBeansXMLBuilder builder) {
				super(appConfig, modelConfig, builder);
			}

			@Override
			protected void configureProcessDeleteTask() {
				// Add Delete Children Task
				this.createProcessDeleteTask().c().list().ref(
				        LoginKeyIterateControllerConfigurer.LoginKeyDeleteByParentIterateTaskConfigurer.SCHEDULE_TASK_NAME);
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

	public static class LoginKeyIterateControllerConfigurer extends CustomLocalModelIterateControllerConfigurerImpl {

		@Override
		protected ConfigurerInstance makeInstance(AppConfiguration appConfig,
		                                          AppModelConfiguration modelConfig,
		                                          SpringBeansXMLBuilder builder) {
			return new LoginIterateConfigurerInstance(appConfig, modelConfig, builder);
		}

		protected class LoginIterateConfigurerInstance extends IterateConfigurerInstance {

			public LoginIterateConfigurerInstance(AppConfiguration appConfig,
			        AppModelConfiguration modelConfig,
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
			protected void configureFilteredScheduleTask(AppModelConfiguration modelConfig,
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
