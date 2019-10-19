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
public class TestPasswordLoginTokenContextImpl extends AbstractLoginPointerTestLoginTokenContextImpl
        implements TestLoginTokenContext {

	private PasswordLoginController passwordController;
	private LoginRegisterService registerService;

	public ObjectifyRegistry<LoginPointer> loginPointerRegistry;
	public ObjectifyRegistry<Login> loginRegistry;

	public TestPasswordLoginTokenContextImpl(PasswordLoginController passwordController,
	        LoginRegisterService registerService,
	        LoginTokenService<LoginToken> service,
	        ObjectifyRegistry<LoginPointer> loginPointerRegistry,
	        ObjectifyRegistry<Login> loginRegistry) {
		super(service);
		this.passwordController = passwordController;
		this.registerService = registerService;
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

	// MARK: TestLoginTokenContext
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
	public void setLogin(LoginToken token) {
		String pointerId = token.getLoginPointerId();
		LoginPointer pointer = this.loginPointerRegistry.get(new ModelKey(pointerId));
		this.setLogin(pointer);
	}

}
