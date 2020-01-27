package com.dereekb.gae.server.auth.security.login.oauth.impl.service;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
import com.dereekb.gae.server.auth.security.login.exception.LoginDisabledException;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginService;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link OAuthLoginService} implementation.
 *
 * @author dereekb
 *
 */
public class OAuthLoginServiceImpl
        implements OAuthLoginService {

	private boolean emailRequired = false;

	private LoginPointerService loginPointerService;

	public OAuthLoginServiceImpl(LoginPointerService loginPointerService) {
		this.setLoginPointerService(loginPointerService);
	}

	public LoginPointerService getLoginPointerService() {
		return this.loginPointerService;
	}

	public void setLoginPointerService(LoginPointerService loginPointerService) {
		if (loginPointerService == null) {
			throw new IllegalArgumentException("loginPointerService cannot be null.");
		}

		this.loginPointerService = loginPointerService;
	}

	public boolean isEmailRequired() {
		return this.emailRequired;
	}

	public void setEmailRequired(boolean emailRequired) {
		this.emailRequired = emailRequired;
	}

	// MARK: OAuthLoginService
	@Override
	public LoginPointer login(OAuthAuthorizationInfo authCode)
	        throws LoginDisabledException,
	            OAuthInsufficientException {
		OAuthLoginInfo info = authCode.getLoginInfo();

		LoginPointerType type = info.getLoginType();
		String identifier = info.getId();
		String email = info.getEmail();

		if (identifier == null) {
			throw new OAuthInsufficientException("Id was not available for login.");
		}

		if (email == null && this.emailRequired) {
			throw new OAuthInsufficientException("Email was required and not available for login.");
		}

		ModelKey key = type.makeKey(identifier);

		LoginPointer template = new LoginPointer();
		template.setModelKey(key);
		template.setLoginPointerType(type);
		template.setEmail(email);

		return this.loginPointerService.getOrCreateLoginPointer(key, template);
	}

	@Override
	public String toString() {
		return "OAuthLoginServiceImpl [loginPointerService=" + this.loginPointerService + "]";
	}

}
