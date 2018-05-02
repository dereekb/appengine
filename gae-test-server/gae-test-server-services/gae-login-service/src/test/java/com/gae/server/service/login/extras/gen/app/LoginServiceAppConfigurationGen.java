package com.gae.server.service.login.extras.gen.app;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.impl.GaeAppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.AppServicesConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractServiceAppConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.AppGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.LoginGroupConfigurationGen;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class LoginServiceAppConfigurationGen extends AbstractServiceAppConfigurationGen {

	@Override
	public AppConfiguration makeAppConfiguration() {

		// Models
		// Login
		AppModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		AppModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		List<AppModelConfigurationGroup> modelConfigurations = ListUtility
		        .toList((AppModelConfigurationGroup) loginGroup, appGroup);

		AppServiceConfigurationInfo appServiceConfigurationInfo = new GaeAppServiceConfigurationInfo("gae-test-server",
		        "login", "v1");

		// Services


		// Configuration
		AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer = null;
		AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer = null;

		AppServicesConfigurer appServicesConfigurer = new AppServicesConfigurerImpl(appLoginTokenSecurityConfigurer,
		        appWebHookEventServiceConfigurer);

		AppConfigurationImpl configuration = new AppConfigurationImpl(appServiceConfigurationInfo, null,
		        modelConfigurations);

		configuration.setAppName("GAE Test Login Service");

		configuration.setAppTaskQueueName("login");
		configuration.setAppId(1L);

		return configuration;
	}

}
