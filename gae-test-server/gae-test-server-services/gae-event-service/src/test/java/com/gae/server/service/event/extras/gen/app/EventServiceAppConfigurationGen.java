package com.gae.server.service.event.extras.gen.app;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractServiceAppConfigurationGen;

public class EventServiceAppConfigurationGen extends AbstractServiceAppConfigurationGen {

	@Override
	public AppConfiguration makeAppConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	@Override
	public AppConfiguration makeAppConfiguration() {

		// TODO: Add model configurations.

		List<AppModelConfigurationGroup> modelConfigurations = ListUtility.toList();

		AppConfigurationImpl configuration = new AppConfigurationImpl(modelConfigurations);

		configuration.setAppName("gae-event-service");
		configuration.setAppVersion("v1");
		configuration.setAppServiceName("event");
		configuration.setAppTaskQueueName("event");
		configuration.setAppId(1L);

		return configuration;
	}
	*/

}
