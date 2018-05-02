package com.dereekb.gae.extras.gen.app.gae.local;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomLocalModelContextConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomLocalModelCrudConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomLocalModelRoleSetLoaderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.SecurityModelQueryInitializerConfigurerImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class AppGroupConfigurationGen {

	public static AppModelConfigurationGroupImpl makeLocalAppGroupConfig() {

		// App
		AppModelConfigurationImpl appModel = makeAppModelConfig();
		AppModelConfigurationImpl appHookModel = makeAppHookModelConfig();

		AppModelConfigurationGroupImpl appGroup = new AppModelConfigurationGroupImpl("app",
		        ListUtility.toList(appModel, appHookModel));

		return appGroup;
	}

	public static AppModelConfigurationImpl makeAppModelConfig() {
		AppModelConfigurationImpl appModel = new AppModelConfigurationImpl(App.class);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("loginOwnedModelQuerySecurityDelegate"));

		appModel.setCustomModelContextConfigurer(customLocalModelContextConfigurer);

		return appModel;
	}

	public static AppModelConfigurationImpl makeAppHookModelConfig() {
		AppModelConfigurationImpl appHookModel = new AppModelConfigurationImpl(AppHook.class);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer.setCustomLocalModelCrudConfigurer(new AppHookCrudConfigurer());
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("appOwnedModelQuerySecurityDelegate"));
		customLocalModelContextConfigurer
		        .setCustomLocalModelRoleSetLoaderConfigurer(new AppParentSecurityRoleConfigurer());

		appHookModel.setCustomModelContextConfigurer(customLocalModelContextConfigurer);

		return appHookModel;
	}

	// MARK: Internal
	private static class AppParentSecurityRoleConfigurer extends CustomLocalModelRoleSetLoaderConfigurerImpl {

		@Override
		public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
		                                                  AppModelConfiguration modelConfig,
		                                                  SpringBeansXMLBuilder builder) {
			this.saferMakeRoleBuilderComponent(modelConfig, builder).c().ref("appParentModelRoleSetContextReader");
			this.makeRoleSetLoaderComponent(modelConfig, builder, true);
		}

	}

	public static class AppHookCrudConfigurer extends CustomLocalModelCrudConfigurerImpl {

		@Override
		public void configureCrudServiceComponents(AppConfiguration appConfig,
		                                           AppModelConfiguration modelConfig,
		                                           SpringBeansXMLBuilder builder) {
			new AppHookCrudConfigurerInstance(appConfig, modelConfig, builder).configure();
		}

		protected class AppHookCrudConfigurerInstance extends CrudConfigurerInstance {

			public AppHookCrudConfigurerInstance(AppConfiguration appConfig,
			        AppModelConfiguration modelConfig,
			        SpringBeansXMLBuilder builder) {
				super(appConfig, modelConfig, builder);
			}

			@Override
			protected SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder>> configureCreateAttributeUpdater(SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> builder,
			                                                                                                                                 String attributeUpdaterBeanId) {
				return super.configureCreateAttributeUpdater(builder, attributeUpdaterBeanId)
				        .ref("appRelatedModelAttributeUtility");
			}

		}

	}
}
