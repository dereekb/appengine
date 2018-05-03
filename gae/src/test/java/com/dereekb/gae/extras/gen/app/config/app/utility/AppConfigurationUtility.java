package com.dereekb.gae.extras.gen.app.config.app.utility;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.utilities.collections.SingleItem;

public class AppConfigurationUtility {

	public static List<LocalModelConfiguration> readModelConfigurations(AppConfiguration config) {
		return AppConfigurationUtility.readModelConfigurations(config.getModelConfigurations());
	}

	public static List<LocalModelConfiguration> readModelConfigurations(LocalModelConfigurationGroup group) {
		return AppConfigurationUtility.readModelConfigurations(SingleItem.withValue(group));
	}

	public static List<LocalModelConfiguration> readModelConfigurations(Iterable<LocalModelConfigurationGroup> groups) {
		List<LocalModelConfiguration> configs = new ArrayList<LocalModelConfiguration>();

		for (LocalModelConfigurationGroup group : groups) {
			configs.addAll(group.getModelConfigurations());
		}

		return configs;
	}

}
