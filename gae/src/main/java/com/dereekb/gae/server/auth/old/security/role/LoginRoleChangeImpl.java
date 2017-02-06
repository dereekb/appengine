package com.dereekb.gae.server.auth.old.security.role;

import java.util.List;

/**
 * Default implementation of {@link LoginRoleChange}.
 * 
 * @author dereekb
 */
public class LoginRoleChangeImpl
        implements LoginRoleChange {

	private List<? extends Role> rolesToAdd;
	private List<? extends Role> rolesToRemove;

	public LoginRoleChangeImpl(List<? extends Role> rolesToAdd, List<? extends Role> rolesToRemove) {
		this.rolesToAdd = rolesToAdd;
		this.rolesToRemove = rolesToRemove;
    }

	@Override
	public List<? extends Role> getRolesToAdd() {
		return this.rolesToAdd;
	}

	public void setRolesToAdd(List<? extends Role> rolesToAdd) {
		this.rolesToAdd = rolesToAdd;
    }

	@Override
	public List<? extends Role> getRolesToRemove() {
		return this.rolesToRemove;
    }

	public void setRolesToRemove(List<? extends Role> rolesToRemove) {
		this.rolesToRemove = rolesToRemove;
    }

}
