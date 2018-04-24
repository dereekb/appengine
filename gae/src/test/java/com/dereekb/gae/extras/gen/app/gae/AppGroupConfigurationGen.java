package com.dereekb.gae.extras.gen.app.gae;

import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.CustomLocalModelContextConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.SecurityModelQueryInitializerConfigurerImpl;
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

		appModel.setCustomLocalModelContextConfigurer(customLocalModelContextConfigurer);

		return appModel;
	}

	public static AppModelConfigurationImpl makeAppHookModelConfig() {
		AppModelConfigurationImpl appHookModel = new AppModelConfigurationImpl(AppHook.class);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("appOwnedModelQuerySecurityDelegate"));

		appHookModel.setCustomLocalModelContextConfigurer(customLocalModelContextConfigurer);

		return appHookModel;
	}

}
