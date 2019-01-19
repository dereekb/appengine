package com.dereekb.gae.server.auth.old.security.role;

import java.util.List;

import com.dereekb.gae.server.auth.model.login.Login;

/**
 * Interface that contains {@link Role} changes for a {@link Login}.
 *
 * @author dereekb
 *
 * @see LoginRoleWriter
 */
@Deprecated
public interface LoginRoleChange {

	public List<? extends Role> getRolesToAdd();

	public List<? extends Role> getRolesToRemove();

}
