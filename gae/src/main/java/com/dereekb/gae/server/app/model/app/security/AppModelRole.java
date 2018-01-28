package com.dereekb.gae.server.app.model.app.security;

import com.dereekb.gae.server.auth.security.model.roles.IndexCodedModelRole;

/**
 * {@link App} model roles.
 *
 * @author dereekb
 *
 */
public enum AppModelRole implements IndexCodedModelRole {

	/**
	 * Whether or not the user can manage hooks.
	 */
	MANAGE_HOOKS(20, "app_manage_hooks");

	public final int code;
	public final String role;

	private AppModelRole(final int code, final String role) {
		this.code = code;
		this.role = role;
	}

	@Override
	public Integer getCode() {
		return this.code;
	}

	@Override
	public String getRole() {
		return this.role;
	}

}
