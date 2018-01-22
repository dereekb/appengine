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

	private String appId;
	private String appService;
	private AppVersion appVersion;

	public AppServiceVersionInfoImpl(String appId, String appService, AppVersion appVersion) {
		this.setAppId(appId);
		this.setAppService(appService);
		this.setAppVersion(appVersion);
	}

	@Override
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		if (appId == null) {
			throw new IllegalArgumentException("appId cannot be null.");
		}

		this.appId = appId;
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
		return "AppVersionInfoImpl [appId=" + this.appId + ", appService=" + this.appService + ", appVersion="
		        + this.appVersion + "]";
	}

}

