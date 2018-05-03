package com.dereekb.gae.extras.gen.app.gae.remote;

import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class RemoteLoginServiceConfigurationGen {

	public LocalModelConfigurationGroupImpl makeRemoteLoginGroupConfig() {

		// Login
		LocalModelConfigurationImpl loginModel = makeLoginModelConfig();
		LocalModelConfigurationImpl loginPointerModel = makeLoginPointerModelConfig();

		LocalModelConfigurationGroupImpl loginGroup = new LocalModelConfigurationGroupImpl("login",
		        ListUtility.toList(loginModel, loginPointerModel));
		return loginGroup;
	}

	public LocalModelConfigurationImpl makeLoginModelConfig() {
		return null;
	}

	public LocalModelConfigurationImpl makeLoginPointerModelConfig() {
		return null;
	}

}
