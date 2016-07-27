package com.dereekb.gae.web.api.auth.controller.register;

import java.util.List;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * {@link LoginRegisterController} delegate.
 *
 * @author dereekb
 *
 */
public interface LoginRegisterControllerDelegate {

	/**
	 * Registers a new {@link Login} for the {@link LoginPointer}.
	 *
	 * @return New {@link LoginTokenPair}.
	 * @throws TokenUnauthorizedException
	 *             Thrown if the current token is not authorized for
	 *             registering, such as an anonymous token.
	 * @throws LoginExistsException
	 *             Thrown if the current token already is linked to a login.
	 */
	public LoginTokenPair register() throws TokenUnauthorizedException, LoginExistsException;

	/**
	 * Registers multiple logins for the {@link LoginPointer}.
	 *
	 * @param tokens
	 *            Encoded tokens. Never empty or null.
	 * @throws TokenUnauthorizedException
	 *             Thrown if one or more tokens are invalid.
	 * @throws IllegalArgumentException
	 *             Thrown if one or more logins are referenced.
	 */
	public void registerLogins(List<String> tokens) throws TokenUnauthorizedException, IllegalArgumentException;

}
