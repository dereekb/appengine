package com.dereekb.gae.test.server.auth.impl;

import com.dereekb.gae.model.extension.generation.impl.keys.LongModelKeyGenerator;
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
import com.dereekb.gae.utilities.factory.Factory;
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
public class TestPasswordLoginTokenContextImpl
        implements TestLoginTokenContext {

	private static final String TEST_USERNAME = "username";
	private static final String TEST_PASSWORD = "password";

	private PasswordLoginController passwordController;
	private LoginRegisterService registerService;
	private LoginTokenService service;

	public ObjectifyRegistry<LoginPointer> loginPointerRegistry;
	public ObjectifyRegistry<Login> loginRegistry;

	private Factory<ModelKey> nameFactory = new LongModelKeyGenerator();

	private LoginPointer pointer = null;

	private Long encodedRoles = null;
	private Long encodedAdminRoles = null;
	private Integer group = null;

	private boolean defaultToAnonymous = true;

	private String username = TEST_USERNAME;
	private String password = TEST_PASSWORD;

	public TestPasswordLoginTokenContextImpl(PasswordLoginController passwordController,
	        LoginRegisterService registerService,
	        LoginTokenService service,
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

	public LoginTokenService getService() {
		return this.service;
	}

	public void setService(LoginTokenService service) {
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

	public Long getEncodedRoles() {
		return this.encodedRoles;
	}

	public void setEncodedRoles(Long encodedRoles) {
		this.encodedRoles = encodedRoles;
	}

	public Long getEncodedAdminRoles() {
		return this.encodedAdminRoles;
	}

	public void setEncodedAdminRoles(Long encodedAdminRoles) {
		this.encodedAdminRoles = encodedAdminRoles;
	}

	public Integer getGroup() {
		return this.group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDefaultToAnonymous() {
		return this.defaultToAnonymous;
	}

	public void setDefaultToAnonymous(boolean defaultToAnonymous) {
		this.defaultToAnonymous = defaultToAnonymous;
	}

	// MARK: TestLoginTokenContext
	@Override
	public String getToken() {
		String token = null;

		if (this.pointer != null) {
			token = this.service.encodeLoginToken(this.pointer);
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
	public TestLoginTokenPair generateSystemAdmin() {
		return this.generateSystemAdmin(this.nameFactory.make().toString());
	}

	@Override
	public TestLoginTokenPair generateSystemAdmin(String username) {
		return this.generateLogin(username, this.encodedAdminRoles);
	}

	@Override
	public TestLoginTokenPair generateLogin() {
		return this.generateLogin(this.nameFactory.make().toString());
	}

	@Override
	public TestLoginTokenPair generateLogin(String username) {
		return this.generateLogin(username, this.encodedRoles);
	}

	@Override
	public TestLoginTokenPair generateLogin(String username,
	                                        Long roles) {
		LoginTokenPair primary = this.passwordController.create(username, this.password);
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
		login.setGroup(this.group);
		login.setRoles(roles);
		this.loginRegistry.save(login, false);

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

	private class TestLoginTokenPairImpl
	        implements TestLoginTokenPair {

		private Login login;
		private LoginPointer loginPointer;

		public TestLoginTokenPairImpl(Login login, LoginPointer loginPointer) {
			this.login = login;
			this.loginPointer = loginPointer;
		}

		@Override
		public Login getLogin() {
			return this.login;
		}

		@Override
		public LoginPointer getLoginPointer() {
			return this.loginPointer;
		}

	}

}
