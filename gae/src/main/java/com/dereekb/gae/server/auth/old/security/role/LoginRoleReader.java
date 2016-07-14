package com.dereekb.gae.server.auth.old.security.role;

import java.util.List;

import com.dereekb.gae.server.auth.model.login.Login;

/**
 * Used to read a set of roles from a {@link Login}.
 *
 * @author dereekb
 */
@Deprecated
public interface LoginRoleReader {

	public List<? extends Role> rolesForLogin(Login login);

}
