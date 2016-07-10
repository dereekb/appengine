package com.dereekb.gae.web.api.auth.controller.register.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.auth.controller.register.LoginRegisterControllerDelegate;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link LoginRegisterControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public class LoginRegisterControllerDelegateImpl
        implements LoginRegisterControllerDelegate {

	private LoginRegisterService registerService;
	private LoginTokenService tokenService;

	public LoginRegisterControllerDelegateImpl(LoginRegisterService registerService, LoginTokenService tokenService) {
		this.registerService = registerService;
		this.tokenService = tokenService;
	}

	public LoginRegisterService getRegisterService() {
    	return this.registerService;
    }

    public void setRegisterService(LoginRegisterService registerService) {
    	this.registerService = registerService;
    }

	public LoginTokenService getTokenService() {
		return this.tokenService;
	}

	public void setTokenService(LoginTokenService tokenService) {
		this.tokenService = tokenService;
	}

	// MARK: LoginRegisterControllerDelegate
	@Override
	public LoginTokenPair register() throws LoginExistsException {
		LoginTokenAuthentication authentication = LoginSecurityContext.getAuthentication();
		LoginTokenUserDetails details = authentication.getPrincipal();
		LoginPointer pointer = details.getLoginPointer();

		if (pointer == null) {
			throw new RuntimeException("No pointer specified.");
		}

		Login login = this.registerService.register(pointer);

		// Temporarily set for token, without having to load new pointer for
		// encoding.
		pointer.setLogin(login.getObjectifyKey());

		String token = this.tokenService.encodeLoginToken(pointer);
		return LoginTokenPair.build(pointer, token);
	}

	@Override
	public void registerLogins(List<String> tokens) throws TokenUnauthorizedException, IllegalArgumentException {
		Long primaryLoginId = null;
		Set<String> loginPointers = new HashSet<String>();

		for (String token : tokens) {
			LoginToken loginToken = this.tokenService.decodeLoginToken(token);
			Long loginId = loginToken.getLogin();
			String loginPointer = loginToken.getLoginPointer();

			if (primaryLoginId == null) {
				primaryLoginId = loginId;
			} else if (loginId != null && primaryLoginId.equals(loginId) == false) {
				throw new IllegalArgumentException("Multiple logins were presented.");
			}

			if (loginPointer != null) {
				loginPointers.add(loginPointer);
			}
		}

		if (primaryLoginId == null) {
			throw new IllegalArgumentException("No login was found.");
		}

		this.registerService.registerLogins(new ModelKey(primaryLoginId), loginPointers);
	}


}
