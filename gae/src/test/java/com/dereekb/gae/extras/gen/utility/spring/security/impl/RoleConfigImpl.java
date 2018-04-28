package com.dereekb.gae.extras.gen.utility.spring.security.impl;

import com.dereekb.gae.extras.gen.utility.spring.security.RoleConfig;

/**
 * {@link RoleConfig} implementation.
 *
 * @author dereekb
 *
 */
public class RoleConfigImpl
        implements RoleConfig {

	private String access;

	public RoleConfigImpl(String access) {
		this.setAccess(access);
	}

	public static RoleConfig makel(String access) {
		return new RoleConfigImpl(access);
	}

	@Override
	public String getAccess() {
		return this.access;
	}

	public void setAccess(String access) {
		if (access == null) {
			throw new IllegalArgumentException("access cannot be null.");
		}

		this.access = access;
	}

	@Override
	public String toString() {
		return "RoleConfigImpl [access=" + this.access + "]";
	}

}
