package com.dereekb.gae.server.auth.security.system.impl;

import com.dereekb.gae.server.auth.security.app.service.ConfiguredAppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenResponse;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * {@link SystemLoginTokenFactory} implementation.
 *
 * @author dereekb
 *
 */
public class SystemLoginTokenFactoryImpl
        implements SystemLoginTokenFactory {

	private Long roles;

	private SystemLoginTokenService systemLoginTokenService;
	private ConfiguredAppLoginSecuritySigningService signingService;

	public SystemLoginTokenFactoryImpl(SystemLoginTokenService systemLoginTokenService,
	        ConfiguredAppLoginSecuritySigningService signingService) {
		this.setSystemLoginTokenService(systemLoginTokenService);
		this.setSigningService(signingService);
	}

	public Long getRoles() {
		return this.roles;
	}

	public void setRoles(Long roles) {
		this.roles = roles;
	}

	public SystemLoginTokenService getSystemLoginTokenService() {
		return this.systemLoginTokenService;
	}

	public void setSystemLoginTokenService(SystemLoginTokenService systemLoginTokenService) {
		if (systemLoginTokenService == null) {
			throw new IllegalArgumentException("systemLoginTokenService cannot be null.");
		}

		this.systemLoginTokenService = systemLoginTokenService;
	}

	public ConfiguredAppLoginSecuritySigningService getSigningService() {
		return this.signingService;
	}

	public void setSigningService(ConfiguredAppLoginSecuritySigningService signingService) {
		if (signingService == null) {
			throw new IllegalArgumentException("signingService cannot be null.");
		}

		this.signingService = signingService;
	}

	// MARK: SystemLoginTokenFactory
	@Override
	public SignedEncodedLoginToken makeSystemToken() throws FactoryMakeFailureException {
		SystemLoginTokenResponse token = this.makeLoginToken();
		String encodedToken = token.getEncodedLoginToken();
		return this.signingService.signWithToken(encodedToken, "");
	}

	public SystemLoginTokenResponse makeLoginToken() {
		ModelKey appId = this.signingService.getAppDetails().getModelKey();

		SystemLoginTokenRequestImpl systemTokenRequest = new SystemLoginTokenRequestImpl(appId.toString());
		systemTokenRequest.setRoles(this.roles);

		return this.systemLoginTokenService.makeSystemToken(systemTokenRequest);
	}

	@Override
	public String toString() {
		return "SystemLoginTokenFactoryImpl [roles=" + this.roles + ", systemLoginTokenService="
		        + this.systemLoginTokenService + ", signingService=" + this.signingService + "]";
	}

}
