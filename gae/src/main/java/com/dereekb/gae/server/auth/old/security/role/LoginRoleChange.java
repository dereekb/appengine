package com.dereekb.gae.server.auth.old.security.role;

import java.util.List;

/**
 * Interface that contains {@link Role} changes for a {@link Login}.
 *
 * @author dereekb
 * @see {@link LoginRoleWriter}
 */
public interface LoginRoleChange {

	public List<? extends Role> getRolesToAdd();

	public List<? extends Role> getRolesToRemove();

}
