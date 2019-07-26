package com.dereekb.gae.test.server.auth.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;

/**
 * {@link TestLoginTokenContext} for remote systems. It does a minimum amount of
 * work.
 *
 * @author dereekb
 *
 */
public class TestRemoteLoginSystemLoginTokenContextImpl extends AbstractLoginPointerTestLoginTokenContextImpl
        implements TestLoginTokenContext {

	public TestRemoteLoginSystemLoginTokenContextImpl(LoginTokenService<LoginToken> service) {
		super(service);
	}

	// MARK: TestLoginTokenContext
	@Override
	public TestLoginTokenPair generateLogin(String username,
	                                        Long roles) {

		LoginPointer pointer = new LoginPointer(username);
		pointer.setLoginPointerType(LoginPointerType.PASSWORD);

		Login login = new Login();

		// TODO: Setup login...

		return new TestLoginTokenPairImpl(login, pointer);
	}

	@Override
	public void setLogin(LoginToken token) {
		String pointerId = token.getLoginPointerId();
		LoginPointer pointer = new LoginPointer(pointerId);

		// TODO: Set pointer, etc.

		this.setLogin(pointer);
	}




}
