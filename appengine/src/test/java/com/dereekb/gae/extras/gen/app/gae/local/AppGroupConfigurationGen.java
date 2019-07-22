package com.dereekb.gae.extras.gen.app.gae.local;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.LocalModelContextConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.LocalModelCrudConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.LocalModelRoleSetLoaderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.SecurityModelQueryInitializerConfigurerImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * Local {@link App} group generation.
 *
 * @author dereekb
 *
 */
public class AppGroupConfigurationGen {

	public static LocalModelConfigurationGroupImpl makeLocalAppGroupConfig() {

		// App
		LocalModelConfigurationImpl appModel = makeAppModelConfig();
		LocalModelConfigurationImpl appHookModel = makeAppHookModelConfig();

		LocalModelConfigurationGroupImpl appGroup = new LocalModelConfigurationGroupImpl("app",
		        ListUtility.toList(appModel, appHookModel));

		return appGroup;
	}

	public static LocalModelConfigurationGroupImpl makeInternalLocalAppGroupConfig() {

		// App
		LocalModelConfigurationImpl appModel = makeInternalAppModelConfig();

		LocalModelConfigurationGroupImpl appGroup = new LocalModelConfigurationGroupImpl("app",
		        ListUtility.toList(appModel));

		return appGroup;
	}

	public static LocalModelConfigurationImpl makeAppModelConfig() {
		LocalModelConfigurationImpl appModel = new LocalModelConfigurationImpl(App.class);

		LocalModelContextConfigurerImpl customLocalModelContextConfigurer = new LocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("loginOwnedModelQuerySecurityDelegate"));

		appModel.setCustomModelContextConfigurer(customLocalModelContextConfigurer);

		return appModel;
	}

	public static LocalModelConfigurationImpl makeInternalAppModelConfig() {
		LocalModelConfigurationImpl readOnlyAppModel = new LocalModelConfigurationImpl(App.class);

		readOnlyAppModel.setInternalModelOnly(true);
		readOnlyAppModel.setIsReadOnly();

		return readOnlyAppModel;
	}

	public static LocalModelConfigurationImpl makeAppHookModelConfig() {
		LocalModelConfigurationImpl appHookModel = new LocalModelConfigurationImpl(AppHook.class);

		LocalModelContextConfigurerImpl customLocalModelContextConfigurer = new LocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer.setCustomLocalModelCrudConfigurer(new AppHookCrudConfigurer());
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("appOwnedModelQuerySecurityDelegate"));
		customLocalModelContextConfigurer
		        .setCustomLocalModelRoleSetLoaderConfigurer(new AppParentSecurityRoleConfigurer());

		appHookModel.setCustomModelContextConfigurer(customLocalModelContextConfigurer);

		return appHookModel;
	}

	// MARK: Internal
	private static class AppParentSecurityRoleConfigurer extends LocalModelRoleSetLoaderConfigurerImpl {

		@Override
		public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
		                                                  LocalModelConfiguration modelConfig,
		                                                  SpringBeansXMLBuilder builder) {
			this.saferMakeRoleBuilderComponent(modelConfig, builder).c().ref("appParentModelRoleSetContextReader");
			this.makeRoleSetLoaderComponent(modelConfig, builder, true);
		}

	}

	public static class AppHookCrudConfigurer extends LocalModelCrudConfigurerImpl {

		@Override
		public void configureCrudServiceComponents(AppConfiguration appConfig,
		                                           LocalModelConfiguration modelConfig,
		                                           SpringBeansXMLBuilder builder) {
			new AppHookCrudConfigurerInstance(appConfig, modelConfig, builder).configure();
		}

		protected class AppHookCrudConfigurerInstance extends LocalModelCrudConfigurerInstance {

			public AppHookCrudConfigurerInstance(AppConfiguration appConfig,
			        LocalModelConfiguration modelConfig,
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
