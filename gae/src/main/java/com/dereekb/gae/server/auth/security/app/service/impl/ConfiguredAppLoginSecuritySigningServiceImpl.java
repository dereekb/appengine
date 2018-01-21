package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.app.service.ConfiguredAppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;

/**
 * {@link ConfiguredAppLoginSecuritySigningService} implementation that wraps a
 * {@link AppLoginSecuritySigningService}.
 *
 * @author dereekb
 *
 */
public class ConfiguredAppLoginSecuritySigningServiceImpl
        implements ConfiguredAppLoginSecuritySigningService {

	private AppLoginSecurityDetails appSecurityDetails;
	private AppLoginSecuritySigningService service;

	public AppLoginSecurityDetails getAppSecurityDetails() {
		return this.appSecurityDetails;
	}

	public void setAppSecurityDetails(AppLoginSecurityDetails appSecurityDetails) {
		if (appSecurityDetails == null) {
			throw new IllegalArgumentException("appSecurityDetails cannot be null.");
		}

		this.appSecurityDetails = appSecurityDetails;
	}

	public AppLoginSecuritySigningService getService() {
		return this.service;
	}

	public void setService(AppLoginSecuritySigningService service) {
		if (service == null) {
			throw new IllegalArgumentException("service cannot be null.");
		}

		this.service = service;
	}

	protected String getSecret() {
		return this.appSecurityDetails.getAppSecret();
	}

	// MARK: ConfiguredAppLoginSecuritySigningService
	@Override
	public SignedEncodedLoginToken signToken(String token) {
		return this.signToken(this.getSecret(), token);
	}

	@Override
	public String hexSign(String token) throws IllegalArgumentException {
		return this.hexSign(this.getSecret(), token);
	}

	@Override
	public byte[] byteSign(String token) throws IllegalArgumentException {
		return this.byteSign(this.getSecret(), token);
	}

	// MARK: AppLoginSecuritySigningService
	@Override
	public AppLoginSecurityDetails getAppDetails() {
		return this.appSecurityDetails;
	}

	@Override
	public SignedEncodedLoginToken signToken(String secret,
	                                         String token)
	        throws IllegalArgumentException {
		return this.service.signToken(secret, token);
	}

	@Override
	public String hexSign(String secret,
	                      String token)
	        throws IllegalArgumentException {
		return this.service.hexSign(secret, token);
	}

	@Override
	public byte[] byteSign(String secret,
	                       String token)
	        throws IllegalArgumentException {
		return this.service.byteSign(secret, token);
	}

	@Override
	public String toString() {
		return "ConfiguredAppLoginSecuritySigningServiceImpl [appSecurityDetails=" + this.appSecurityDetails
		        + ", service=" + this.service + "]";
	}

}
