package com.gae.server.service.login.extras.gen.app;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractServiceAppConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.AppGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.LoginGroupConfigurationGen;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class LoginServiceAppConfigurationGen extends AbstractServiceAppConfigurationGen {

	@Override
	public AppConfiguration makeAppConfiguration() {

		// Login
		AppModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		AppModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		List<AppModelConfigurationGroup> modelConfigurations = ListUtility
		        .toList((AppModelConfigurationGroup) loginGroup, appGroup);

		AppConfigurationImpl configuration = new AppConfigurationImpl(modelConfigurations);

		configuration.setAppName("gae-login-service");
		configuration.setAppVersion("v1");
		configuration.setAppServiceName("login");
		configuration.setAppTaskQueueName("login");
		configuration.setAppId(1L);

		return configuration;
	}

}
