package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.app.service.LoginTokenVerifierRequest;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;

/**
 * Abstract {@link AppLoginSecurityVerifierService} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractAppLoginSecurityVerifierServiceImpl
        implements AppLoginSecurityVerifierService {

	// MARK: AppLoginSecurityVerifierService
	@Override
	public boolean isValidTokenSignature(LoginTokenVerifierRequest request) {
		try {
			this.assertValidTokenSignature(request);
			return true;
		} catch (TokenException e) {
			return false;
		}
	}

}
