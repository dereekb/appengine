package com.gae.server.service.login.extras.gen.app;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractServiceAppConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.AppGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.LoginGroupConfigurationGen;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class LoginServiceAppConfigurationGen extends AbstractServiceAppConfigurationGen {

	public AppConfiguration makeAppConfiguration() {

		// Login
		AppModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		AppModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		List<AppModelConfigurationGroup> modelConfigurations = ListUtility
		        .toList((AppModelConfigurationGroup) loginGroup, appGroup);

		AppConfigurationImpl configuration = new AppConfigurationImpl(modelConfigurations);

		configuration.setAppName("gae-test-login");
		configuration.setAppVersion("v1");
		configuration.setAppServiceName("login");
		configuration.setAppTaskQueueName("login");
		configuration.setAppId(1L);

		return configuration;
	}

}
