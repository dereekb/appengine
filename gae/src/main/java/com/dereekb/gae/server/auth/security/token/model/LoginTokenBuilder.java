package com.dereekb.gae.server.auth.security.token.model;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;


public interface LoginTokenBuilder {

	public LoginToken buildLoginToken(LoginPointer pointer);

}
