package com.gae.server.service.event.extras.gen.app;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractServiceAppConfigurationGen;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class EventServiceAppConfigurationGen extends AbstractServiceAppConfigurationGen {

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

}
