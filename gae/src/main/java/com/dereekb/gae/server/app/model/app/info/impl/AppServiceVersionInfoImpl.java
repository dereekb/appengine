package com.dereekb.gae.server.app.model.app.info.impl;

import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.server.app.model.app.info.AppVersion;

/**
 * {@link AppServiceVersionInfo} implementation.
 *
 * @author dereekb
 *
 */
public class AppServiceVersionInfoImpl
        implements AppServiceVersionInfo {

	private String appProjectId;
	private String appService;
	private AppVersion appVersion;

	public AppServiceVersionInfoImpl(String appProjectId, String appService, String majorAppVersion) {
		this(appProjectId, appService, new AppVersionImpl(majorAppVersion));
	}

	public AppServiceVersionInfoImpl(String appProjectId, String appService, AppVersion appVersion) {
		this.setAppProjectId(appProjectId);
		this.setAppService(appService);
		this.setAppVersion(appVersion);
	}

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
	public String getAppService() {
		return this.appService;
	}

	public void setAppService(String appService) {
		if (appService == null) {
			throw new IllegalArgumentException("appService cannot be null.");
		}

		this.appService = appService;
	}

	@Override
	public AppVersion getAppVersion() {
		return this.appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		if (appVersion == null) {
			throw new IllegalArgumentException("appVersion cannot be null.");
		}

		this.appVersion = appVersion;
	}

	@Override
	public String toString() {
		return "AppVersionInfoImpl [appProjectId=" + this.appProjectId + ", appService=" + this.appService
		        + ", appVersion=" + this.appVersion + "]";
	}

}
