package com.dereekb.gae.server.auth.security.system.impl;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenRequest;

/**
 * {@link SystemLoginTokenRequest} implementation.
 *
 * @author dereekb
 *
 */
public class SystemLoginTokenRequestImpl
        implements SystemLoginTokenRequest {

	private String appId;
	private Long expiresIn;
	private Long roles;

	public SystemLoginTokenRequestImpl(String appId) {
		this.setAppId(appId);
	}

	// MARK: SystemTokenRequest
	@Override
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		if (appId == null) {
			throw new IllegalArgumentException("appId cannot be null.");
		}

		this.appId = appId;
	}

	@Override
	public Long getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public Long getRoles() {
		return this.roles;
	}

	public void setRoles(Long roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "SystemTokenRequestImpl [appId=" + this.appId + ", expiresIn=" + this.expiresIn + ", roles=" + this.roles
		        + "]";
	}

}
