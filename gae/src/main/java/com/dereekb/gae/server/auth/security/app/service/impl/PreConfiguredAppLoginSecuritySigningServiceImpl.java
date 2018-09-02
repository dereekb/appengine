package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.app.service.ConfiguredAppLoginSecuritySigningService;

/**
 * {@link ConfiguredAppLoginSecuritySigningService} implementation that is
 * pre-configured with {@link AppLoginSecurityDetails}.
 *
 * @author dereekb
 *
 */
public class PreConfiguredAppLoginSecuritySigningServiceImpl extends AbstractConfiguredAppLoginSecuritySigningServiceImpl {

	private AppLoginSecurityDetails appSecurityDetails;

	public PreConfiguredAppLoginSecuritySigningServiceImpl(AppLoginSecurityDetails appSecurityDetails) {
		this(AppLoginSecuritySigningServiceImpl.HmacSHA256(), appSecurityDetails);
	}

	public PreConfiguredAppLoginSecuritySigningServiceImpl(AppLoginSecuritySigningService service,
	        AppLoginSecurityDetails appSecurityDetails) {
		super(service);
		this.setAppSecurityDetails(appSecurityDetails);
	}

	public AppLoginSecurityDetails getAppSecurityDetails() {
		return this.appSecurityDetails;
	}

	public void setAppSecurityDetails(AppLoginSecurityDetails appSecurityDetails) {
		if (appSecurityDetails == null) {
			throw new IllegalArgumentException("appSecurityDetails cannot be null.");
		}

		this.appSecurityDetails = appSecurityDetails;
	}

	// MARK: AppLoginSecuritySigningService
	@Override
	public AppLoginSecurityDetails getAppDetails() {
		return this.appSecurityDetails;
	}

	@Override
	public String toString() {
		return "PreConfiguredAppLoginSecuritySigningServiceImpl [appSecurityDetails=" + this.appSecurityDetails
		        + ", getService()=" + this.getService() + "]";
	}

}
