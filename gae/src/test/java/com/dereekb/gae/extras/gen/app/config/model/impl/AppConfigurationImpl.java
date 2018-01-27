package com.dereekb.gae.extras.gen.app.config.model.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppConfigurationImpl
        implements AppConfiguration {

	private List<AppModelConfigurationGroup> modelConfigurations;

	public AppConfigurationImpl(List<AppModelConfigurationGroup> modelConfigurations) {
		this.setModelConfigurations(modelConfigurations);
	}

	// MARK: AppConfiguration
	@Override
	public List<AppModelConfigurationGroup> getModelConfigurations() {
		return this.modelConfigurations;
	}

	public void setModelConfigurations(List<AppModelConfigurationGroup> modelConfigurations) {
		if (modelConfigurations == null) {
			throw new IllegalArgumentException("modelConfigurations cannot be null.");
		}

		this.modelConfigurations = modelConfigurations;
	}

}
