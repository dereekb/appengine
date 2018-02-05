package com.dereekb.gae.extras.gen.app.config.model.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfigurationImpl;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppConfigurationImpl
        implements AppConfiguration {

	private AppBeansConfiguration appBeans = new AppBeansConfigurationImpl();
	private List<AppModelConfigurationGroup> modelConfigurations;

	public AppConfigurationImpl(List<AppModelConfigurationGroup> modelConfigurations) {
		this.setModelConfigurations(modelConfigurations);
	}

	// MARK: AppConfiguration
	@Override
	public AppBeansConfiguration getAppBeans() {
		return this.appBeans;
	}

	public void setAppBeans(AppBeansConfiguration appBeans) {
		if (appBeans == null) {
			throw new IllegalArgumentException("appBeans cannot be null.");
		}

		this.appBeans = appBeans;
	}

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
