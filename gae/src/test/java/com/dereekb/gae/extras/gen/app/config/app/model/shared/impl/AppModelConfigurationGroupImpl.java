package com.dereekb.gae.extras.gen.app.config.app.model.shared.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AppModelConfigurationGroupImpl<T extends AppModelConfiguration> {

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
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

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

}
