package com.dereekb.gae.server.auth.old.security.role;

import com.dereekb.gae.server.auth.model.login.Login;

/**
 * Pre-configured extension of {@link LoginRoleWriter} that updates the passed
 * {@link Login} using it's configuration.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface ConfiguredLoginRoleWriter {

	public void changeRoles(Login login);

}
