package com.dereekb.gae.server.auth.security.token.model;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Used for building a {@link LoginToken} from a {@link LoginPointer} for
 * authentication.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenBuilder {

	public LoginToken buildLoginToken(LoginPointer pointer);

}
