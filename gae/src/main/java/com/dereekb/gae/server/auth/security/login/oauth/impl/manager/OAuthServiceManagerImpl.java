package com.dereekb.gae.server.auth.security.login.oauth.impl.manager;

import java.util.Map;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginService;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthServiceManager;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthServiceUnavailableException;

/**
 * {@link OAuthServiceManager} implementation.
 *
 * @author dereekb
 *
 */
public class OAuthServiceManagerImpl
        implements OAuthServiceManager {

	private OAuthLoginService loginService;
	private Map<LoginPointerType, OAuthService> services;

	public OAuthServiceManagerImpl(OAuthLoginService loginService, Map<LoginPointerType, OAuthService> services)
	        throws IllegalArgumentException {
		this.setLoginService(loginService);
		this.setServices(services);
	}

	public OAuthLoginService getLoginService() {
		return this.loginService;
	}

	public void setLoginService(OAuthLoginService loginService) throws IllegalArgumentException {
		if (loginService == null) {
			throw new IllegalArgumentException();
		}

		this.loginService = loginService;
	}

	public Map<LoginPointerType, OAuthService> getServices() {
		return this.services;
	}

	public void setServices(Map<LoginPointerType, OAuthService> services) throws IllegalArgumentException {
		if (services == null) {
			throw new IllegalArgumentException();
		}

		this.services = services;
	}

	// MARK: OAuthServiceManager
	@Override
	public OAuthService getService(LoginPointerType type) throws OAuthServiceUnavailableException {
		OAuthService service = this.services.get(type);

		if (service == null) {
			throw new OAuthServiceUnavailableException();
		}

		return service;
	}

	// MARK: OAuthLoginService
	@Override
	public LoginPointer login(OAuthAuthorizationInfo authCode) throws OAuthInsufficientException {
		return this.loginService.login(authCode);
	}

	@Override
	public String toString() {
		return "OAuthServiceManagerImpl [loginService=" + this.loginService + ", services=" + this.services + "]";
	}

}
