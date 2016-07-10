package com.dereekb.gae.server.auth.security.login;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;


public interface NewLoginGenerator {

	public Login makeLogin(LoginPointer pointer);

}
