package com.dereekb.gae.extras.gen.app.config.app.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.local.LoginTokenAppSecurityBeansConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfigurationImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppConfigurationImpl
        implements AppConfiguration {

	private Long appId = 1L;
	private String appName = "app";
	private String appTaskQueueName = "app";

	private AppServicesConfigurer appServicesConfigurer;
	private AppServiceConfigurationInfo appServiceConfigurationInfo = new GaeAppServiceConfigurationInfo("app", "app");

	private boolean isRootServer = false;
	private boolean isLoginServer = true;

	private AppBeansConfiguration appBeans = new AppBeansConfigurationImpl();
	private AppSecurityBeansConfigurer appSecurityBeansConfigurer = new LoginTokenAppSecurityBeansConfigurerImpl();

	private List<AppModelConfigurationGroup> localModelConfigurations;
	private List<AppRemoteServiceConfiguration> remoteServices = Collections.emptyList();

	public AppConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        AppServicesConfigurer appServicesConfigurer) {
		this.setAppServicesConfigurer(appServicesConfigurer);
	}

	public AppConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        AppServicesConfigurer appServicesConfigurer,
	        List<AppModelConfigurationGroup> localModelConfigurations) {
		this.setAppServiceConfigurationInfo(appServiceConfigurationInfo);
		this.setAppServicesConfigurer(appServicesConfigurer);
		this.setLocalModelConfigurations(localModelConfigurations);
	}

	// MARK: AppConfiguration
	@Override
	public Long getAppId() {
		return this.appId;
	}

	public void setAppId(Long appId) {
		if (appId == null || appId == 0L) {
			throw new IllegalArgumentException("appId cannot be null or 0.");
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
	public String getAppTaskQueueName() {
		return this.appTaskQueueName;
	}

	public void setAppTaskQueueName(String appTaskQueueName) {
		if (appTaskQueueName == null) {
			throw new IllegalArgumentException("appTaskQueueName cannot be null.");
		}

		this.appTaskQueueName = appTaskQueueName;
	}

	@Override
	public AppServicesConfigurer getAppServicesConfigurer() {
		return this.appServicesConfigurer;
	}

	public void setAppServicesConfigurer(AppServicesConfigurer appServicesConfigurer) {
		if (appServicesConfigurer == null) {
			throw new IllegalArgumentException("appServicesConfigurer cannot be null.");
		}

		this.appServicesConfigurer = appServicesConfigurer;
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
	public boolean isRootServer() {
		return this.isRootServer;
	}

	public void setRootServer(boolean isRootServer) {
		this.isRootServer = isRootServer;
	}

	@Override
	public boolean isLoginServer() {
		return this.isLoginServer;
	}

	public void setLoginServer(boolean isLoginServer) {
		this.isLoginServer = isLoginServer;
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
	public AppSecurityBeansConfigurer getAppSecurityBeansConfigurer() {
		return this.appSecurityBeansConfigurer;
	}

	public void setAppSecurityBeansConfigurer(AppSecurityBeansConfigurer appSecurityBeansConfigurer) {
		if (appSecurityBeansConfigurer == null) {
			throw new IllegalArgumentException("appSecurityBeansConfigurer cannot be null.");
		}

		this.appSecurityBeansConfigurer = appSecurityBeansConfigurer;
	}

	public List<AppRemoteServiceConfiguration> getRemoteServices() {
		return this.remoteServices;
	}

	public void setRemoteServices(List<AppRemoteServiceConfiguration> remoteServices) {
		if (remoteServices == null) {
			throw new IllegalArgumentException("remoteServices cannot be null.");
		}

		this.remoteServices = remoteServices;
	}

	@Override
	public List<AppModelConfigurationGroup> getModelConfigurations() {
		List<AppModelConfigurationGroup> models = ListUtility.copy(this.localModelConfigurations);

		for (AppRemoteServiceConfiguration service : this.remoteServices) {
			models.addAll(service.getServiceModelConfigurations());
		}

		return models;
	}

	@Override
	public List<AppModelConfigurationGroup> getLocalModelConfigurations() {
		return this.localModelConfigurations;
	}

	public void setLocalModelConfigurations(List<AppModelConfigurationGroup> localModelConfigurations) {
		if (localModelConfigurations == null) {
			throw new IllegalArgumentException("localModelConfigurations cannot be null.");
		}

		this.localModelConfigurations = localModelConfigurations;
	}

}
