package com.dereekb.gae.extras.gen.app.config.app.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;

/**
 * {@link AppServiceConfigurationInfo} implementation for Google App Engine.
 *
 * @author dereekb
 *
 */
public class AppServiceConfigurationInfoImpl
        implements AppServiceConfigurationInfo {

	public String appProjectId;
	public String appServiceName;
	public String appVersion;

	public AppServiceConfigurationInfoImpl(String appProjectId, String appServiceName) {
		this(appProjectId, appServiceName, "v1");
	}

	public AppServiceConfigurationInfoImpl(String appProjectId, String appServiceName, String appVersion) {
		this.setAppProjectId(appProjectId);
		this.setAppServiceName(appServiceName);
		this.setAppVersion(appVersion);
	}

	// MARK: AppServiceConfigurationInfo
	@Override
	public String getAppProjectId() {
		return this.appProjectId;
	}

	public void setAppProjectId(String appProjectId) {
		if (appProjectId == null) {
			throw new IllegalArgumentException("appProjectId cannot be null.");
		}

		this.appProjectId = appProjectId;
	}

	@Override
	public String getAppServiceName() {
		return this.appServiceName;
	}

	public void setAppServiceName(String appServiceName) {
		if (appServiceName == null) {
			throw new IllegalArgumentException("appServiceName cannot be null.");
		}

		this.appServiceName = appServiceName;
	}

	@Override
	public String getAppVersion() {
		return this.appVersion;
	}

	public void setAppVersion(String appVersion) {
		if (appVersion == null) {
			throw new IllegalArgumentException("appVersion cannot be null.");
		}

		this.appVersion = appVersion;
	}

	@Override
	public String getFullAppApiPath() {
		return GoogleAppEngineUtility.urlForService(this.getAppProjectId(), this.getAppServiceName(),
		        this.getAppVersion()) + this.getRootAppApiPath();
	}

	@Override
	public String getRootAppApiPath() {
		return "/api/" + this.getAppServiceName() + "/" + this.getAppVersion();
	}

}
