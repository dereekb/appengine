package com.dereekb.gae.test.server.auth.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginController;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link TestLoginTokenContext} implementation that uses a
 * {@link PasswordLoginController}
 *
 * @author dereekb
 *
 */
public class TestPasswordLoginTokenContextImpl extends AbstractTestLoginTokenContextImpl
        implements TestLoginTokenContext {

	private PasswordLoginController passwordController;
	private LoginRegisterService registerService;
	private LoginTokenService<LoginToken> service;

	public ObjectifyRegistry<LoginPointer> loginPointerRegistry;
	public ObjectifyRegistry<Login> loginRegistry;

	private LoginPointer pointer = null;

	public TestPasswordLoginTokenContextImpl(PasswordLoginController passwordController,
	        LoginRegisterService registerService,
	        LoginTokenService<LoginToken> service,
	        ObjectifyRegistry<LoginPointer> loginPointerRegistry,
	        ObjectifyRegistry<Login> loginRegistry) {
		this.passwordController = passwordController;
		this.registerService = registerService;
		this.service = service;
		this.loginPointerRegistry = loginPointerRegistry;
		this.loginRegistry = loginRegistry;
	}

	public PasswordLoginController getPasswordController() {
		return this.passwordController;
	}

	public void setPasswordController(PasswordLoginController passwordController) {
		this.passwordController = passwordController;
	}

	public LoginRegisterService getRegisterService() {
		return this.registerService;
	}

	public void setRegisterService(LoginRegisterService registerService) {
		this.registerService = registerService;
	}

	public LoginTokenService<LoginToken> getService() {
		return this.service;
	}

	public void setService(LoginTokenService<LoginToken> service) {
		this.service = service;
	}

	public ObjectifyRegistry<LoginPointer> getLoginPointerRegistry() {
		return this.loginPointerRegistry;
	}

	public void setLoginPointerRegistry(ObjectifyRegistry<LoginPointer> loginPointerRegistry) {
		this.loginPointerRegistry = loginPointerRegistry;
	}

	public ObjectifyRegistry<Login> getLoginRegistry() {
		return this.loginRegistry;
	}

	public void setLoginRegistry(ObjectifyRegistry<Login> loginRegistry) {
		this.loginRegistry = loginRegistry;
	}

	public LoginPointer getPointer() {
		return this.pointer;
	}

	public void setPointer(LoginPointer pointer) {
		this.pointer = pointer;
	}

	// MARK: TestLoginTokenContext
	@Override
	public String getToken() {
		String token = null;

		if (this.pointer != null) {
			token = this.service.encodeLoginToken(this.pointer, this.isRefreshAllowed());
		} else if (this.isDefaultToAnonymous()) {
			token = this.service.encodeAnonymousLoginToken("LOGIN-TOKEN");
		}

		return token;
	}

	@Override
	public String generateAnonymousToken() {
		return this.service.encodeAnonymousLoginToken("ANONYMOUS");
	}

	@Override
	public void generateAnonymousLogin() {
		this.clearLogin();
		this.setDefaultToAnonymous(true);
	}

	@Override
	public TestLoginTokenPair generateLogin(String username,
	                                        Long roles) {
		LoginTokenPair primary = this.passwordController.create(username, this.getPassword());
		LoginPointer pointer = this.loginPointerRegistry.get(primary.getLoginPointerKey());
		Login login;

		try {
			login = this.registerService.register(pointer);
		} catch (LoginRegistrationRejectedException e) {
			throw new ApiLoginException(ApiLoginException.LoginExceptionReason.REJECTED, e);
		} catch (LoginExistsException e) {
			throw new RuntimeException(e);
		}

		login.setRoot(true);
		login.setGroup(this.getGroup());
		login.setRoles(roles);
		this.loginRegistry.update(login);

		pointer.setLogin(login.getObjectifyKey());
		this.setLogin(pointer);

		return new TestLoginTokenPairImpl(login, pointer);
	}

	@Override
	public void clearLogin() {
		this.pointer = null;
		this.setDefaultToAnonymous(false);
	}

	@Override
	public void setLogin(LoginToken token) {
		String pointerId = token.getLoginPointerId();
		LoginPointer pointer = this.loginPointerRegistry.get(new ModelKey(pointerId));
		this.setLogin(pointer);
	}

	@Override
	public void setLogin(LoginPointer pointer) {
		this.pointer = pointer;
	}

	/**
	 * {@link TestLoginTokenPair} implementation.
	 *
	 * @author dereekb
	 *
	 */
	private class TestLoginTokenPairImpl extends AbstractTestLoginTokenPairImpl
	        implements TestLoginTokenPair {

		public TestLoginTokenPairImpl(Login login, LoginPointer loginPointer) {
			super(login, loginPointer);
		}

		@Override
		public String makeNewToken() {
			return TestPasswordLoginTokenContextImpl.this.service.encodeLoginToken(this.getLoginPointer(),
			        TestPasswordLoginTokenContextImpl.this.isRefreshAllowed());
		}

	}

}
