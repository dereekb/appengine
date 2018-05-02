package com.dereekb.gae.extras.gen.app.gae.remote;

import com.dereekb.gae.extras.gen.app.config.app.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomRemoteModelContextConfigurerImpl;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class RemoteLoginServiceConfigurationGen {

	public AppModelConfigurationGroupImpl makeRemoteLoginGroupConfig() {

		// Login
		AppModelConfigurationImpl loginModel = makeLoginModelConfig();
		AppModelConfigurationImpl loginPointerModel = makeLoginPointerModelConfig();

		AppModelConfigurationGroupImpl loginGroup = new AppModelConfigurationGroupImpl("login",
		        ListUtility.toList(loginModel, loginPointerModel));
		return loginGroup;
	}

	public AppModelConfigurationImpl makeLoginModelConfig() {
		AppModelConfigurationImpl loginModel = new AppModelConfigurationImpl(Login.class);

		loginModel.setHasCreateService(false);

		CustomRemoteModelContextConfigurerImpl customRemoteModelContextConfigurer = new CustomRemoteModelContextConfigurerImpl();

		loginModel.setCustomModelContextConfigurer(customRemoteModelContextConfigurer);

		return loginModel;
	}

	public AppModelConfigurationImpl makeLoginPointerModelConfig() {
		AppModelConfigurationImpl loginPointerModel = new AppModelConfigurationImpl(LoginPointer.class);

		loginPointerModel.setHasCreateService(false);

		CustomRemoteModelContextConfigurerImpl customRemoteModelContextConfigurer = new CustomRemoteModelContextConfigurerImpl();

		loginPointerModel.setCustomModelContextConfigurer(customRemoteModelContextConfigurer);

		return loginPointerModel;
	}

}
