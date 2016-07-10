package com.dereekb.gae.server.auth.security.login.impl;

import java.util.Set;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.NewLoginGeneratorDelegate;

/**
 * {@link NewLoginGeneratorDelegate} that can apply roles.
 * 
 * @author dereekb
 */
public class NewLoginGeneratorDelegateImpl
        implements NewLoginGeneratorDelegate {

	private Set<Integer> defaultRoles;

	public Set<Integer> getDefaultRoles() {
		return this.defaultRoles;
	}

	public void setDefaultRoles(Set<Integer> defaultRoles) {
		this.defaultRoles = defaultRoles;
	}

	@Override
	public Login makeNewLogin(LoginPointer pointer) {
		Login login = new Login();
		login.setRoles(this.defaultRoles);
		return login;
	}

}
