package com.dereekb.gae.extras.gen.app.config.app.model.shared.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfigurationGroup;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AppModelConfigurationGroupImpl<T extends AppModelConfiguration>
        implements AppModelConfigurationGroup {

	private String groupName;
	private List<T> modelConfigurations;

	public AppModelConfigurationGroupImpl(List<T> modelConfigurations) {
		this(null, modelConfigurations);
	}

	public AppModelConfigurationGroupImpl(String groupName, List<T> modelConfigurations) {
		this.setGroupName(groupName);
		this.setModelConfigurations(modelConfigurations);
	}

	// MARK: AppModelConfigurationGroup
	@Override
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public List<T> getModelConfigurations() {
		return this.modelConfigurations;
	}

	public void setModelConfigurations(List<T> modelConfigurations) {
		if (modelConfigurations == null) {
			throw new IllegalArgumentException("modelConfigurations cannot be null.");
		}

		this.modelConfigurations = modelConfigurations;
	}

	@Override
	public String toString() {
		return "AppModelConfigurationGroupImpl [groupName=" + this.groupName + ", modelConfigurations="
		        + this.modelConfigurations + "]";
	}

	// MARK: Utility
	public static <T extends AppModelConfiguration, G extends AppModelConfigurationGroup> List<T> readModelConfigurations(G group) {
		return readModelConfigurations(SingleItem.withValue(group));
	}

	public static <T extends AppModelConfiguration, G extends AppModelConfigurationGroup> List<T> readModelConfigurations(Iterable<? extends G> groups) {
		List<T> list = new ArrayList<T>();

		for (G group : groups) {
			@SuppressWarnings("unchecked")
			Iterable<T> modelConfigs = (Iterable<T>) group.getModelConfigurations();

			for (T modelConfig : modelConfigs) {
				list.add(modelConfig);
			}
		}

		return list;
	}

}
