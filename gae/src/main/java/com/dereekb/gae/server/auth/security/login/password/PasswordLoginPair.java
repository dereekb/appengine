package com.dereekb.gae.server.auth.security.login.password;

import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginController;


/**
 * Username/password pair.
 *
 * @author dereekb
 * @see {@link PasswordLoginController}
 */
public interface PasswordLoginPair {

	public String getUsername();

	public String getPassword();

}
