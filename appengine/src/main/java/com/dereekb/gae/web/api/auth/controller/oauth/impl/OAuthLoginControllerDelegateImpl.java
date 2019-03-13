package com.dereekb.gae.web.api.auth.controller.oauth.impl;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthCode;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthServiceManager;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthServiceUnavailableException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAccessTokenImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAuthCodeImpl;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
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

	private boolean refreshAllowed = true;
	private OAuthServiceManager manager;
	private LoginTokenService<LoginToken> tokenService;

	public OAuthLoginControllerDelegateImpl(OAuthServiceManager manager, LoginTokenService<LoginToken> tokenService) {
		this.setManager(manager);
		this.setTokenService(tokenService);
	}

	public OAuthServiceManager getManager() {
		return this.manager;
	}

	public void setManager(OAuthServiceManager manager) {
		this.manager = manager;
	}

	public LoginTokenService<LoginToken> getTokenService() {
		return this.tokenService;
	}

	public void setTokenService(LoginTokenService<LoginToken> tokenService) {
		this.tokenService = tokenService;
	}

	public boolean isRefreshAllowed() {
		return this.refreshAllowed;
	}

	public void setRefreshAllowed(boolean refreshAllowed) {
		this.refreshAllowed = refreshAllowed;
	}

	// MARK: OAuthLoginControllerDelegate
	@Deprecated
	public LoginTokenPair login(String type,
	                            HttpServletRequest request)
	        throws OAuthInsufficientException,
	            OAuthServiceUnavailableException,
	            OAuthConnectionException {

		OAuthService service = this.manager.getService(type);
		OAuthAuthorizationInfo authInfo = service.processAuthorizationCodeResponse(request);

		return this.loginWithAuthInfo(authInfo);
	}

	@Override
	public LoginTokenPair loginWithAuthCode(String type,
	                                        String authCode,
	                                        String codeType)
	        throws OAuthConnectionException,
	            OAuthInsufficientException,
	            OAuthAuthorizationTokenRequestException,
	            OAuthServiceUnavailableException {

		OAuthService service = this.manager.getService(type);
		OAuthAuthCode code = new OAuthAuthCodeImpl(authCode, codeType);
		OAuthAuthorizationInfo authInfo = service.processAuthorizationCode(code);
		return this.loginWithAuthInfo(authInfo);
	}

	@Override
	public LoginTokenPair loginWithAccessToken(String type,
	                                           String accessToken)
	        throws OAuthInsufficientException,
	            OAuthServiceUnavailableException,
	            OAuthConnectionException {

		OAuthService service = this.manager.getService(type);
		OAuthAccessToken token = new OAuthAccessTokenImpl(accessToken);
		OAuthAuthorizationInfo authInfo = service.retrieveAuthorizationInfo(token);
		return this.loginWithAuthInfo(authInfo);
	}

	private LoginTokenPair loginWithAuthInfo(OAuthAuthorizationInfo authInfo) {
		LoginPointer pointer = this.manager.login(authInfo);
		String encodedToken = this.tokenService.encodeLoginToken(pointer, this.refreshAllowed);
		LoginTokenPair pair = new LoginTokenPair(pointer.getIdentifier(), encodedToken);
		return pair;
	}

	@Override
	public String toString() {
		return "OAuthLoginControllerDelegateImpl [tokenService=" + this.tokenService + ", manager=" + this.manager
		        + "]";
	}

}
