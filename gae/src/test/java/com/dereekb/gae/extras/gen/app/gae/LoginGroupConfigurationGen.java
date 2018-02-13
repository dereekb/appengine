package com.dereekb.gae.extras.gen.app.gae;

import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationImpl;
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

		return loginModel;
	}

	public static AppModelConfigurationImpl makeLoginPointerModelConfig() {
		AppModelConfigurationImpl loginPointerModel = new AppModelConfigurationImpl(LoginPointer.class);

		return loginPointerModel;
	}

	public static AppModelConfigurationImpl makeLoginKeyModelConfig() {
		AppModelConfigurationImpl loginKeyModel = new AppModelConfigurationImpl(LoginKey.class);

		return loginKeyModel;
	}

}
