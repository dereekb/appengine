package com.dereekb.gae.extras.gen.app.config.app.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;

/**
 * {@link AppServiceConfigurationInfo} implementation for Google App Engine.
 *
 * @author dereekb
 *
 */
public class AppServiceConfigurationInfoImpl
        implements AppServiceConfigurationInfo {

	public static final String DEFAULT_API_VERSION = "v1";

	private String apiVersion;

	private String appProjectId;
	private String appServiceName;
	private String appVersion;

	public AppServiceConfigurationInfoImpl(AppServiceVersionInfo serviceVersionInfo, String apiVersion) {
		this(serviceVersionInfo.getAppProjectId(), serviceVersionInfo.getAppService(),
		        serviceVersionInfo.getAppVersion().getMajorVersion());
	}

	public AppServiceConfigurationInfoImpl(String appProjectId, String appServiceName, String appVersion) {
		this(appProjectId, appServiceName, appVersion, DEFAULT_API_VERSION);
	}

	public AppServiceConfigurationInfoImpl(String appProjectId,
	        String appServiceName,
	        String appVersion,
	        String apiVersion) {
		this.setAppProjectId(appProjectId);
		this.setAppServiceName(appServiceName);
		this.setAppVersion(appVersion);
		this.setApiVersion(apiVersion);
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
		this.appVersion = appVersion;
	}

	public String getApiVersion() {
		return this.apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		if (apiVersion == null) {
			throw new IllegalArgumentException("apiVersion cannot be null.");
		}

		this.apiVersion = apiVersion;
	}

	@Override
	public String getAppServicePath() {
		return GoogleAppEngineUtility.urlForService(this.getAppProjectId(), this.getAppServiceName(),
		        this.getAppVersion());
	}

	@Override
	public String getFullDomainAppApiPath() {
		return this.getAppServicePath() + this.getFullDomainRootAppApiPath();
	}

	@Override
	public String getFullDomainRootAppApiPath() {
		return "/api/" + this.getAppServiceName() + "/" + this.getApiVersion();
	}

}
