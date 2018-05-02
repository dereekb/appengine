package com.dereekb.gae.extras.gen.app.config.app.model.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.impl.GaeAppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.AppServicesConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.local.LoginTokenAppSecurityBeansConfigurerImpl;
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

	private Long appId = 1L;
	private String appName = "app";
	private String appTaskQueueName = "app";

	private AppServicesConfigurer appServicesConfigurer = new AppServicesConfigurerImpl();
	private AppServiceConfigurationInfo serviceConfigurationInfo = new GaeAppServiceConfigurationInfo("app", "app");

	private boolean isRootServer = false;
	private boolean isLoginServer = true;

	private AppSecurityBeansConfigurer appSecurityBeansConfigurer = new LoginTokenAppSecurityBeansConfigurerImpl();
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

	public AppServiceConfigurationInfo getServiceConfigurationInfo() {
		return this.serviceConfigurationInfo;
	}

	public void setServiceConfigurationInfo(AppServiceConfigurationInfo serviceConfigurationInfo) {
		if (serviceConfigurationInfo == null) {
			throw new IllegalArgumentException("serviceConfigurationInfo cannot be null.");
		}

		this.serviceConfigurationInfo = serviceConfigurationInfo;
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
	public List<AppModelConfigurationGroup> getModelConfigurations() {
		return this.modelConfigurations;
	}

	@Override
	public List<AppModelConfigurationGroup> getLocalModelConfigurations() {
		return this.modelConfigurations;
	}

	public void setModelConfigurations(List<AppModelConfigurationGroup> modelConfigurations) {
		if (modelConfigurations == null) {
			throw new IllegalArgumentException("modelConfigurations cannot be null.");
		}

		this.modelConfigurations = modelConfigurations;
	}

}
