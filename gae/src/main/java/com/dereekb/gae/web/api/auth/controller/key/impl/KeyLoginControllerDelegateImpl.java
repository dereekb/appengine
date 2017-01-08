package com.dereekb.gae.web.api.auth.controller.key.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginAuthenticationService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginInfo;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusServiceManager;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginExistsException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginRejectedException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.key.impl.KeyLoginInfoImpl;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.web.api.auth.controller.key.KeyLoginControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * {@link KeyLoginControllerDelegate} implementation.
 * 
 * @author dereekb
 *
 */
public class KeyLoginControllerDelegateImpl implements KeyLoginControllerDelegate {

	private static final String EXISTED_KEY = "Existed";
	private static final String ENABLED_KEY = "Enabled";
	
	private LoginTokenService tokenService;
	
	private KeyLoginStatusServiceManager serviceManager;
	private KeyLoginAuthenticationService authenticationService;
	
	public KeyLoginControllerDelegateImpl(KeyLoginStatusServiceManager serviceManager,
	        KeyLoginAuthenticationService authenticationService) {
		super();
		this.setServiceManager(serviceManager);
		this.setAuthenticationService(authenticationService);
	}

	public KeyLoginStatusServiceManager getServiceManager() {
		return serviceManager;
	}
	
	public void setServiceManager(KeyLoginStatusServiceManager serviceManager) {
		if (serviceManager == null) {
			throw new IllegalArgumentException("ServiceManager cannot be null.");
		}
		
		this.serviceManager = serviceManager;
	}
	
	public KeyLoginAuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(KeyLoginAuthenticationService authenticationService) {
		if (authenticationService == null) {
			throw new IllegalArgumentException("Authentication service cannot be null.");
		}
		
		this.authenticationService = authenticationService;
	}

	//MARK: KeyLoginControllerDelegate
	@Override
	public ApiResponse enableKeyLogin() {
		KeyLoginStatusService service = this.getLoginStatusService();
		
		ApiResponseImpl response = new ApiResponseImpl();
		
		try {
			service.enable();
		} catch (KeyLoginExistsException e) {
			ApiResponseDataImpl data = new ApiResponseDataImpl();
			data.setType(EXISTED_KEY);
			data.setData(true);
			response.setData(data);
		}
		
		return response;
	}

	@Override
	public ApiResponse getKeyLoginStatus() {
		KeyLoginStatusService service = this.getLoginStatusService();

		ApiResponseImpl response = new ApiResponseImpl();
		boolean enabled = true;

		try {
			service.getKeyLoginPointerKey();
		} catch (KeyLoginUnavailableException e) {
			enabled = false;
		}

		ApiResponseDataImpl data = new ApiResponseDataImpl();
		data.setType(ENABLED_KEY);
		data.setData(enabled);
		
		response.setData(data);
		
		return response;
	}

	@Override
	public LoginTokenPair login(String key,
	                            String verification) throws KeyLoginRejectedException, KeyLoginUnavailableException {
		
		KeyLoginInfo keyLoginInfo = new KeyLoginInfoImpl(key, verification);
		LoginPointer loginPointer = this.authenticationService.login(keyLoginInfo);

		String loginPointerId = loginPointer.getIdentifier();
		String loginToken = this.tokenService.encodeLoginToken(loginPointer);

		return new LoginTokenPair(loginPointerId, loginToken);
	}
	
	//MARK: Internal
	private KeyLoginStatusService getLoginStatusService() {
		LoginTokenAuthentication authentication = LoginSecurityContext.getAuthentication();
		LoginTokenUserDetails details = authentication.getPrincipal();
		
		Login login = details.getLogin();
		return this.serviceManager.getService(login);
	}

	@Override
	public String toString() {
		return "KeyLoginControllerDelegateImpl [serviceManager=" + serviceManager + ", authenticationService="
		        + authenticationService + "]";
	}

}
