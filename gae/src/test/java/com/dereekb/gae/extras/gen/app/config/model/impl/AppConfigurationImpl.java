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

	private Long appId = 0L;
	private String appName = "app";
	private String appTaskQueueName = "app";

	private AppBeansConfiguration appBeans = new AppBeansConfigurationImpl();
	private List<AppModelConfigurationGroup> modelConfigurations;

	public AppConfigurationImpl(List<AppModelConfigurationGroup> modelConfigurations) {
		this.setModelConfigurations(modelConfigurations);
	}

	// MARK: AppConfiguration
	@Override
	public Long getAppId() {
		return this.appId;
	}

	public void setAppId(Long appId) {
		if (appId == null) {
			throw new IllegalArgumentException("appId cannot be null.");
		}

		this.appId = appId;
	}

	@Override
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		if (appName == null) {
			throw new IllegalArgumentException("appName cannot be null.");
		}

		this.appName = appName;
	}

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

	@Override
	public String getAppTaskQueueName() {
		return this.appTaskQueueName;
	}

	public void setAppTaskQueueName(String appTaskQueueName) {
		if (appTaskQueueName == null) {
			throw new IllegalArgumentException("appTaskQueueName cannot be null.");
		}

		this.appTaskQueueName = appTaskQueueName;
	}

}