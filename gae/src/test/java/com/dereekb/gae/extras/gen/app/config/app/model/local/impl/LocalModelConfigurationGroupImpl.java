package com.dereekb.gae.extras.gen.app.config.app.model.local.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelConfigurationGroupImpl
        implements LocalModelConfigurationGroup {

	private String groupName;
	private List<LocalModelConfiguration> modelConfigurations;

	public LocalModelConfigurationGroupImpl(List<LocalModelConfiguration> modelConfigurations) {
		this(null, modelConfigurations);
	}

	public LocalModelConfigurationGroupImpl(String groupName, List<LocalModelConfiguration> modelConfigurations) {
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
	public List<LocalModelConfiguration> getModelConfigurations() {
		return this.modelConfigurations;
	}

	public void setModelConfigurations(List<LocalModelConfiguration> modelConfigurations) {
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
