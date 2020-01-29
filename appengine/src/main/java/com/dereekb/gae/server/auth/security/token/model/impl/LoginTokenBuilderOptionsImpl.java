package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.security.token.model.LoginTokenBuilderOptions;

/**
 * {@link LoginTokenBuilderOptions} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenBuilderOptionsImpl
        implements LoginTokenBuilderOptions {

	private Boolean refreshAllowed = null;
	private Long rolesMask = null;

	public LoginTokenBuilderOptionsImpl() {}

	public LoginTokenBuilderOptionsImpl(Boolean refreshAllowed) {
		this(refreshAllowed, null);
	}

	public LoginTokenBuilderOptionsImpl(Boolean refreshAllowed, Long rolesMask) {
		super();
		this.setRefreshAllowed(refreshAllowed);
		this.setRolesMask(rolesMask);
	}

	@Override
	public Boolean getRefreshAllowed() {
		return this.refreshAllowed;
	}

	public void setRefreshAllowed(Boolean refreshAllowed) {
		this.refreshAllowed = refreshAllowed;
	}

	@Override
	public Long getRolesMask() {
		return this.rolesMask;
	}

	public void setRolesMask(Long rolesMask) {
		this.rolesMask = rolesMask;
	}

	@Override
	public String toString() {
		return "LoginTokenBuilderOptionsImpl [refreshAllowed=" + this.refreshAllowed + ", rolesMask=" + this.rolesMask
		        + "]";
	}

}
