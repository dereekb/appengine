package com.dereekb.gae.web.api.auth.controller.oauth.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthServiceManager;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthServiceUnavailableException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAccessTokenImpl;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.web.api.auth.controller.oauth.OAuthLoginControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link OAuthLoginControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class OAuthLoginControllerDelegateImpl
        implements OAuthLoginControllerDelegate {

	private OAuthServiceManager manager;
	private LoginTokenService tokenService;

	public OAuthLoginControllerDelegateImpl(OAuthServiceManager manager, LoginTokenService tokenService) {
		this.setManager(manager);
		this.setTokenService(tokenService);
	}

	public OAuthServiceManager getManager() {
		return this.manager;
	}

	public void setManager(OAuthServiceManager manager) {
		this.manager = manager;
	}

	public LoginTokenService getTokenService() {
		return this.tokenService;
	}

	public void setTokenService(LoginTokenService tokenService) {
		this.tokenService = tokenService;
	}

	// MARK: OAuthLoginControllerDelegate
	@Override
	public LoginTokenPair login(String type,
	                            String accessToken)
	        throws OAuthInsufficientException,
	            OAuthServiceUnavailableException,
	            OAuthConnectionException {

		OAuthService service = this.manager.getService(type);
		OAuthAccessToken token = new OAuthAccessTokenImpl(accessToken);
		OAuthAuthorizationInfo authInfo = service.getAuthorizationInfo(token);

		LoginPointer pointer = this.manager.login(authInfo);
		String encodedToken = this.tokenService.encodeLoginToken(pointer);
		LoginTokenPair pair = new LoginTokenPair(pointer.getIdentifier(), encodedToken);

		return pair;
	}

	@Override
	public String toString() {
		return "OAuthLoginControllerDelegateImpl [tokenService=" + this.tokenService + ", manager=" + this.manager
		        + "]";
	}

}
