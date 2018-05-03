package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.AppRemoteServiceConfigurer;

/**
 * {@link AppRemoteServiceConfiguration} implementation
 *
 * @author dereekb
 *
 */
public class AppRemoteServiceConfigurationImpl
        implements AppRemoteServiceConfiguration {

	private boolean siblingService = true;

	private AppServiceConfigurationInfo appServiceConfigurationInfo;
	private AppRemoteServiceBeansConfiguration serviceBeansConfiguration;
	private AppRemoteServiceConfigurer appRemoteServiceConfigurer;
	private List<LocalModelConfigurationGroup> serviceModelConfigurations;

	public AppRemoteServiceConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        AppRemoteServiceConfigurer appRemoteServiceConfigurer) {
		this(appServiceConfigurationInfo, appRemoteServiceConfigurer, Collections.emptyList());
	}

	public AppRemoteServiceConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        AppRemoteServiceConfigurer appRemoteServiceConfigurer,
	        List<LocalModelConfigurationGroup> serviceModelConfigurations) {
		super();
		this.setAppServiceConfigurationInfo(appServiceConfigurationInfo);
		this.setAppRemoteServiceConfigurer(appRemoteServiceConfigurer);
		this.setServiceModelConfigurations(serviceModelConfigurations);
		this.setServiceBeansConfiguration(new AppRemoteServiceBeansConfigurationImpl(appServiceConfigurationInfo));
	}

	// MARK: AppRemoteServiceConfiguration
	@Override
	public boolean isSiblingService() {
		return this.siblingService;
	}

	public void setSiblingService(boolean siblingService) {
		this.siblingService = siblingService;
	}

	@Override
	public AppServiceConfigurationInfo getAppServiceConfigurationInfo() {
		return this.appServiceConfigurationInfo;
	}

	public void setAppServiceConfigurationInfo(AppServiceConfigurationInfo appServiceConfigurationInfo) {
		if (appServiceConfigurationInfo == null) {
			throw new IllegalArgumentException("appServiceConfigurationInfo cannot be null.");
		}

		this.appServiceConfigurationInfo = appServiceConfigurationInfo;
	}

	@Override
	public List<LocalModelConfigurationGroup> getServiceModelConfigurations() {
		return this.serviceModelConfigurations;
	}

	public void setServiceModelConfigurations(List<LocalModelConfigurationGroup> serviceModelConfigurations) {
		if (serviceModelConfigurations == null) {
			throw new IllegalArgumentException("serviceModelConfigurations cannot be null.");
		}

		this.serviceModelConfigurations = serviceModelConfigurations;
	}

	@Override
	public AppRemoteServiceBeansConfiguration getServiceBeansConfiguration() {
		return this.serviceBeansConfiguration;
	}

	public void setServiceBeansConfiguration(AppRemoteServiceBeansConfiguration serviceBeansConfiguration) {
		if (serviceBeansConfiguration == null) {
			throw new IllegalArgumentException("serviceBeansConfiguration cannot be null.");
		}

		this.serviceBeansConfiguration = serviceBeansConfiguration;
	}

	@Override
	public AppRemoteServiceConfigurer getAppRemoteServiceConfigurer() {
		return this.appRemoteServiceConfigurer;
	}

	public void setAppRemoteServiceConfigurer(AppRemoteServiceConfigurer appRemoteServiceConfigurer) {
		if (appRemoteServiceConfigurer == null) {
			throw new IllegalArgumentException("appRemoteServiceConfigurer cannot be null.");
		}

		this.appRemoteServiceConfigurer = appRemoteServiceConfigurer;
	}

}