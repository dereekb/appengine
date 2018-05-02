package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceConfiguration;

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
	private List<AppModelConfigurationGroup> serviceModelConfigurations;

	public AppRemoteServiceConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo) {
		this(appServiceConfigurationInfo, Collections.emptyList());
	}

	public AppRemoteServiceConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        List<AppModelConfigurationGroup> serviceModelConfigurations) {
		super();
		this.setAppServiceConfigurationInfo(appServiceConfigurationInfo);
		this.setServiceModelConfigurations(serviceModelConfigurations);
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
	public List<AppModelConfigurationGroup> getServiceModelConfigurations() {
		return this.serviceModelConfigurations;
	}

	public void setServiceModelConfigurations(List<AppModelConfigurationGroup> serviceModelConfigurations) {
		if (serviceModelConfigurations == null) {
			throw new IllegalArgumentException("serviceModelConfigurations cannot be null.");
		}

		this.serviceModelConfigurations = serviceModelConfigurations;
	}

}
