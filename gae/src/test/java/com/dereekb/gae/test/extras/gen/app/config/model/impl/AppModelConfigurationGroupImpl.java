package com.dereekb.gae.test.extras.gen.app.config.model.impl;

import java.util.List;

import com.dereekb.gae.test.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.test.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.test.extras.gen.app.config.model.AppModelConfigurationGroup;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppModelConfigurationGroupImpl
        implements AppModelConfigurationGroup {

	private String groupName;
	private List<AppModelConfiguration> modelConfigurations;

	public AppModelConfigurationGroupImpl(List<AppModelConfiguration> modelConfigurations) {
		this(null, modelConfigurations);
	}

	public AppModelConfigurationGroupImpl(String groupName, List<AppModelConfiguration> modelConfigurations) {
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
	public List<AppModelConfiguration> getModelConfigurations() {
		return this.modelConfigurations;
	}

	public void setModelConfigurations(List<AppModelConfiguration> modelConfigurations) {
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

}
