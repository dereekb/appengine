package com.dereekb.gae.extras.gen.app.config.app;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;
import com.dereekb.gae.utilities.collections.SingleItem;

public class AppConfigurationUtility {

	public static List<AppModelConfiguration> readModelConfigurations(AppConfiguration config) {
		return AppConfigurationUtility.readModelConfigurations(config.getModelConfigurations());
	}

	public static List<AppModelConfiguration> readModelConfigurations(AppModelConfigurationGroup group) {
		return AppConfigurationUtility.readModelConfigurations(SingleItem.withValue(group));
	}

	public static List<AppModelConfiguration> readModelConfigurations(Iterable<AppModelConfigurationGroup> groups) {
		List<AppModelConfiguration> configs = new ArrayList<AppModelConfiguration>();

		for (AppModelConfigurationGroup group : groups) {
			configs.addAll(group.getModelConfigurations());
		}

		return configs;
	}

}
