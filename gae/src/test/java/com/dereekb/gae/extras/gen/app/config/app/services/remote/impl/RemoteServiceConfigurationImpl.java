package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceContextConfigurer;

/**
 * {@link RemoteServiceConfiguration} implementation
 *
 * @author dereekb
 *
 */
public class RemoteServiceConfigurationImpl
        implements RemoteServiceConfiguration {

	private boolean siblingService = true;

	private AppServiceConfigurationInfo appServiceConfigurationInfo;
	private RemoteServiceBeansConfiguration serviceBeansConfiguration;
	private RemoteServiceContextConfigurer remoteServiceContextConfigurer;
	private List<RemoteModelConfigurationGroup> serviceModelConfigurations;

	public RemoteServiceConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        RemoteServiceContextConfigurer remoteServiceContextConfigurer) {
		this(appServiceConfigurationInfo, remoteServiceContextConfigurer, Collections.emptyList());
	}

	public RemoteServiceConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        RemoteServiceContextConfigurer remoteServiceContextConfigurer,
	        List<RemoteModelConfigurationGroup> serviceModelConfigurations) {
		super();
		this.setAppServiceConfigurationInfo(appServiceConfigurationInfo);
		this.setRemoteServiceContextConfigurer(remoteServiceContextConfigurer);
		this.setServiceModelConfigurations(serviceModelConfigurations);
		this.setServiceBeansConfiguration(new RemoteServiceBeansConfigurationImpl(appServiceConfigurationInfo));
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
	public List<RemoteModelConfigurationGroup> getServiceModelConfigurations() {
		return this.serviceModelConfigurations;
	}

	public void setServiceModelConfigurations(List<RemoteModelConfigurationGroup> serviceModelConfigurations) {
		if (serviceModelConfigurations == null) {
			throw new IllegalArgumentException("serviceModelConfigurations cannot be null.");
		}

		this.serviceModelConfigurations = serviceModelConfigurations;
	}

	@Override
	public RemoteServiceBeansConfiguration getServiceBeansConfiguration() {
		return this.serviceBeansConfiguration;
	}

	public void setServiceBeansConfiguration(RemoteServiceBeansConfiguration serviceBeansConfiguration) {
		if (serviceBeansConfiguration == null) {
			throw new IllegalArgumentException("serviceBeansConfiguration cannot be null.");
		}

		this.serviceBeansConfiguration = serviceBeansConfiguration;
	}

	public RemoteServiceContextConfigurer getRemoteServiceContextConfigurer() {
		return this.remoteServiceContextConfigurer;
	}

	public void setRemoteServiceContextConfigurer(RemoteServiceContextConfigurer remoteServiceContextConfigurer) {
		if (remoteServiceContextConfigurer == null) {
			throw new IllegalArgumentException("remoteServiceContextConfigurer cannot be null.");
		}

		this.remoteServiceContextConfigurer = remoteServiceContextConfigurer;
	}

}
