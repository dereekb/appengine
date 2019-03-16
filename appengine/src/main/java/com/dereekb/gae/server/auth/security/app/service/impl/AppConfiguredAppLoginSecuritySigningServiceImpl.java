package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityDetailsService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.app.service.ConfiguredAppLoginSecuritySigningService;

/**
 * {@link ConfiguredAppLoginSecuritySigningService} implementation that uses a
 * {@link AppLoginSecurityDetailsService} to load the details from the
 * configured app.
 *
 * @author dereekb
 *
 */
public class AppConfiguredAppLoginSecuritySigningServiceImpl extends AbstractConfiguredAppLoginSecuritySigningServiceImpl {

	private AppInfo appInfo;
	private AppLoginSecurityDetailsService detailsService;

	public AppConfiguredAppLoginSecuritySigningServiceImpl(AppInfo appInfo, AppLoginSecurityService service) {
		this(service, appInfo, service);
	}

	public AppConfiguredAppLoginSecuritySigningServiceImpl(AppLoginSecuritySigningService service,
	        AppInfo appInfo,
	        AppLoginSecurityDetailsService detailsService) {
		super(service);
		this.setAppInfo(appInfo);
		this.setDetailsService(detailsService);
	}

	public AppInfo getAppInfo() {
		return this.appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		if (appInfo == null) {
			throw new IllegalArgumentException("appInfo cannot be null.");
		}

		this.appInfo = appInfo;
	}

	public AppLoginSecurityDetailsService getDetailsService() {
		return this.detailsService;
	}

	public void setDetailsService(AppLoginSecurityDetailsService detailsService) {
		if (detailsService == null) {
			throw new IllegalArgumentException("detailsService cannot be null.");
		}

		this.detailsService = detailsService;
	}

	// MARK: AbstractConfiguredAppLoginSecuritySigningServiceImpl
	@Override
	public AppLoginSecurityDetails getAppDetails() {
		return this.detailsService.getAppLoginDetails(this.appInfo.getModelKey());
	}

	@Override
	public String toString() {
		return "AppConfiguredAppLoginSecuritySigningServiceImpl [appInfo=" + this.appInfo + ", detailsService="
		        + this.detailsService + ", getService()=" + this.getService() + "]";
	}

}
