package com.dereekb.gae.server.auth.security.login.oauth.impl.service;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.LoginPointerService;
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

	private LoginPointerService loginPointerService;

	public OAuthLoginServiceImpl(LoginPointerService loginPointerService) {
		this.loginPointerService = loginPointerService;
	}

	public LoginPointerService getLoginPointerService() {
		return this.loginPointerService;
	}

	public void setLoginPointerService(LoginPointerService loginPointerService) {
		this.loginPointerService = loginPointerService;
	}

	@Override
	public LoginPointer login(OAuthAuthorizationInfo authCode) throws OAuthInsufficientException {
		OAuthLoginInfo info = authCode.getLoginInfo();

		LoginPointerType type = info.getLoginType();
		String identifier = info.getId();

		ModelKey key = type.makeKey(identifier);

		LoginPointer template = new LoginPointer();
		template.setModelKey(key);
		template.setLoginPointerType(type);

		return this.loginPointerService.getOrCreateLoginPointer(key, template);
	}

	@Override
	public String toString() {
		return "OAuthLoginServiceImpl [loginPointerService=" + this.loginPointerService + "]";
	}

}
