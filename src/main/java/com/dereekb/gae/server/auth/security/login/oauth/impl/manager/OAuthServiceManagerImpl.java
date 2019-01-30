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
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link OAuthServiceManager} implementation.
 *
 * @author dereekb
 *
 */
public class OAuthServiceManagerImpl
        implements OAuthServiceManager {

	private static final Map<String, LoginPointerType> DEFAULT_MAP;

	static {
		Map<String, LoginPointerType> typesMap = new CaseInsensitiveMap<>();
		typesMap.put("google", LoginPointerType.OAUTH_GOOGLE);
		typesMap.put("facebook", LoginPointerType.OAUTH_FACEBOOK);
		DEFAULT_MAP = typesMap;
	}

	private OAuthLoginService loginService;
	private Map<LoginPointerType, OAuthService> services;
	private Map<String, LoginPointerType> typesMap;

	public OAuthServiceManagerImpl(OAuthLoginService loginService, Map<LoginPointerType, OAuthService> services)
	        throws IllegalArgumentException {
		this.setLoginService(loginService);
		this.setServices(services);
		this.useDefaultTypesMap();
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

	private void useDefaultTypesMap() {
		this.setTypesMap(DEFAULT_MAP);
	}

	public Map<String, LoginPointerType> getTypesMap() {
		return this.typesMap;
	}

	public void setTypesMap(Map<String, LoginPointerType> typesMap) {
		this.typesMap = typesMap;
	}

	// MARK: OAuthServiceManager
	@Override
	public OAuthService getService(String type) throws OAuthServiceUnavailableException {
		LoginPointerType pointerType = this.typesMap.get(type);

		if (pointerType == null) {
			throw new OAuthServiceUnavailableException();
		}

		return this.getService(pointerType);
	}

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
