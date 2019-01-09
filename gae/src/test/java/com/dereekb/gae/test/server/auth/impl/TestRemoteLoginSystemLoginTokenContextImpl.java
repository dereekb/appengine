package com.dereekb.gae.test.server.auth.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.test.server.auth.TestLoginTokenContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;

/**
 * {@link TestLoginTokenContext} for remote systems. It does a minimum amount of
 * work.
 *
 * @author dereekb
 *
 */
public class TestRemoteLoginSystemLoginTokenContextImpl extends AbstractTestLoginTokenContextImpl
        implements TestLoginTokenContext {

	@Override
	public TestLoginTokenPair generateLogin(String username,
	                                        Long roles) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearLogin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLogin(LoginToken token) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLogin(LoginPointer pointer) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateAnonymousToken() {
		// TODO Auto-generated method stub
		return null;
	}

}
