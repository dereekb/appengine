package com.dereekb.gae.client.api.auth.token.impl.service;

import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.app.service.LoginTokenVerifierRequest;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;

/**
 * {@link AppLoginSecurityVerifierService} that sends a request to a remote
 * service to verify login tokens.
 *
 * @author dereekb
 *
 */
public class RemoteAppLoginSecurityVerifierServiceImpl
        implements AppLoginSecurityVerifierService {

	// TODO: Use

	// MARK: AppLoginSecurityVerifierService
	@Override
	public boolean isValidTokenSignature(LoginTokenVerifierRequest request) {

		return false;
	}

	@Override
	public void assertValidTokenSignature(LoginTokenVerifierRequest request) throws TokenException {
		// TODO Auto-generated method stub

	}

}
