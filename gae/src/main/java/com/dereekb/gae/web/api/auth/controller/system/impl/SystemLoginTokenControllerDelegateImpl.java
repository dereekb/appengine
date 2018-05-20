package com.dereekb.gae.web.api.auth.controller.system.impl;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenRequest;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenResponse;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenService;
import com.dereekb.gae.server.auth.security.system.exception.SystemLoginTokenServiceException;
import com.dereekb.gae.web.api.auth.controller.system.SystemLoginTokenControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link SystemLoginTokenControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class SystemLoginTokenControllerDelegateImpl
        implements SystemLoginTokenControllerDelegate {

	private SystemLoginTokenService systemLoginTokenService;

	public SystemLoginTokenControllerDelegateImpl(SystemLoginTokenService systemLoginTokenService) {
		super();
		this.setSystemLoginTokenService(systemLoginTokenService);
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

	// MARK: SystemTokenControllerDelegate
	@Override
	public LoginTokenPair makeSystemToken(SystemLoginTokenRequest request) throws SystemLoginTokenServiceException {
		SystemLoginTokenResponse response = this.systemLoginTokenService.makeSystemToken(request);
		LoginTokenPair pair = new LoginTokenPair(response.getEncodedLoginToken());
		return pair;
	}

	@Override
	public String toString() {
		return "SystemLoginTokenControllerDelegateImpl [systemLoginTokenService=" + this.systemLoginTokenService + "]";
	}

}
