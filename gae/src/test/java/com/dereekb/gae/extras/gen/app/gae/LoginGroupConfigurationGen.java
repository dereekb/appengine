package com.dereekb.gae.extras.gen.app.gae;

import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.AdminOnlySecuredQueryInitializerConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.CustomLocalModelContextConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl.SecurityModelQueryInitializerConfigurerImpl;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
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
		        .setSecuredQueryInitializerConfigurer(new AdminOnlySecuredQueryInitializerConfigurerImpl());

		loginModel.setCustomLocalModelContextConfigurer(customLocalModelContextConfigurer);

		return loginModel;
	}

	public static AppModelConfigurationImpl makeLoginPointerModelConfig() {
		AppModelConfigurationImpl loginPointerModel = new AppModelConfigurationImpl(LoginPointer.class);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("loginOwnedModelQuerySecurityDelegate"));

		loginPointerModel.setCustomLocalModelContextConfigurer(customLocalModelContextConfigurer);

		return loginPointerModel;
	}

	public static AppModelConfigurationImpl makeLoginKeyModelConfig() {
		AppModelConfigurationImpl loginKeyModel = new AppModelConfigurationImpl(LoginKey.class);

		CustomLocalModelContextConfigurerImpl customLocalModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();
		customLocalModelContextConfigurer.setSecuredQueryInitializerConfigurer(
		        new SecurityModelQueryInitializerConfigurerImpl("loginOwnedModelQuerySecurityDelegate"));

		loginKeyModel.setCustomLocalModelContextConfigurer(customLocalModelContextConfigurer);

		return loginKeyModel;
	}

}
