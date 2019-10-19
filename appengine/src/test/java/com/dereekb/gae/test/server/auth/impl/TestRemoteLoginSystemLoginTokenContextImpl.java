package com.dereekb.gae.test.server.auth.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;

/**
 * {@link TestLoginTokenContext} for remote systems.
 * <p>
 * It stores a {@link LoginToken} instead of using {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public class TestRemoteLoginSystemLoginTokenContextImpl extends AbstractLoginPointerTestLoginTokenContextImpl
        implements TestLoginTokenContext {

	private TestLoginTokenPair currentLoginToken = null;

	public TestRemoteLoginSystemLoginTokenContextImpl(LoginTokenService<LoginToken> service) {
		super(service);
	}

	@Override
	public LoginPointer getPointer() {
		LoginPointer pointer = null;

		if (this.currentLoginToken != null) {
			pointer = this.currentLoginToken.getLoginPointer();
		}

		return pointer;
	}

	@Override
	public void setPointer(LoginPointer pointer) {
		throw new UnsupportedOperationException("TODO?");
	}

	// MARK: TestLoginTokenContext
	@Override
	public LoginToken getLoginToken() {
		LoginToken token = null;

		if (this.currentLoginToken != null) {
			token = this.currentLoginToken.buildLoginToken();
		}

		return token;
	}

	@Override
	public void clearLogin() {
		super.clearLogin();
		this.currentLoginToken = null;
	}

	@Override
	public TestLoginTokenPair generateLogin(String username,
	                                        Long roles) {

		LoginPointer pointer = new LoginPointer(username);
		pointer.setLoginPointerType(LoginPointerType.PASSWORD);
		this.setLogin(pointer);

		Login login = this.generateTestLogin(username, roles, pointer);

		if (login != null) {
			pointer.setLogin(login.getObjectifyKey());
		}

		TestLoginTokenPair testLoginTokenPair = new TestLoginTokenPairImpl(login, pointer);
		this.currentLoginToken = testLoginTokenPair;
		return testLoginTokenPair;
	}

	@Override
	public void setLogin(LoginToken token) {
		String pointerId = token.getLoginPointerId();
		LoginPointer pointer = new LoginPointer(pointerId);
		this.setLogin(pointer);
	}

	protected Login generateTestLogin(String username,
	                                  Long roles,
	                                  LoginPointer pointer) {

		// Use the hash code of the pointer to have some sort of consistency
		Login login = new Login(new Long(Math.abs(pointer.hashCode())));

		login.setRoot(true);
		login.setRoles(roles);

		return login;
	}

}
