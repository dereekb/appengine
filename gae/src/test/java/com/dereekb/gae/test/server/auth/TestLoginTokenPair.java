package com.dereekb.gae.test.server.auth;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

public interface TestLoginTokenPair {

	public Login getLogin();

	public LoginPointer getLoginPointer();

}
