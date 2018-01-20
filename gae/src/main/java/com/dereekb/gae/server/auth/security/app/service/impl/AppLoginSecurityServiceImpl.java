package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityDetailsService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

public class AppLoginSecurityServiceImpl
        implements AppLoginSecurityService {

	private AppLoginSecuritySigningService signingService;
	private AppLoginSecurityDetailsService detailsService;
	private AppLoginSecurityVerifierService verifierService;

	public AppLoginSecurityServiceImpl(AppLoginSecuritySigningService signingService,
	        AppLoginSecurityDetailsService detailsService,
	        AppLoginSecurityVerifierService verifierService) {
		super();
		this.setSigningService(signingService);
		this.setDetailsService(detailsService);
		this.setVerifierService(verifierService);
	}

	public AppLoginSecuritySigningService getSigningService() {
		return this.signingService;
	}

	public void setSigningService(AppLoginSecuritySigningService signingService) {
		if (signingService == null) {
			throw new IllegalArgumentException("signingService cannot be null.");
		}

		this.signingService = signingService;
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

	public AppLoginSecurityVerifierService getVerifierService() {
		return this.verifierService;
	}

	public void setVerifierService(AppLoginSecurityVerifierService verifierService) {
		if (verifierService == null) {
			throw new IllegalArgumentException("verifierService cannot be null.");
		}

		this.verifierService = verifierService;
	}

	// MARK: AppLoginSecurityService
	@Override
	public AppLoginSecurityDetails getAppLoginDetails(ModelKey appId) {
		return this.detailsService.getAppLoginDetails(appId);
	}

	@Override
	public boolean isValidToken(DecodedLoginToken<?> token,
	                            String signature) {
		return this.verifierService.isValidToken(token, signature);
	}

	@Override
	public String toString() {
		return "AppLoginSecurityServiceImpl [signingService=" + this.signingService + ", detailsService="
		        + this.detailsService + ", verifierService=" + this.verifierService + "]";
	}

}
