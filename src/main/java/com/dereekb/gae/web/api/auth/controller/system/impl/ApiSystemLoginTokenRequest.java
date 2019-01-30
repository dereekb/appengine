package com.dereekb.gae.web.api.auth.controller.system.impl;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.dereekb.gae.server.auth.security.system.SystemLoginTokenRequest;
import com.dereekb.gae.web.api.auth.controller.system.SystemLoginTokenController;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Used by the {@link SystemLoginTokenController} for requesting system tokens.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiSystemLoginTokenRequest
        implements SystemLoginTokenRequest {

	/**
	 * Requested app.
	 */
	@NotEmpty
	private String appId;

	/**
	 * How long the token should live for in milliseconds.
	 */
	@Positive
	private Long expiresIn;

	private Long roles;

	public ApiSystemLoginTokenRequest() {}

	public ApiSystemLoginTokenRequest(String appId) {
		this(appId, null);
	}

	public ApiSystemLoginTokenRequest(String appId, Long expiresIn) {
		this.setAppId(appId);
		this.setExpiresIn(expiresIn);
	}

	public ApiSystemLoginTokenRequest(String appId, Long expiresIn, Long roles) {
		this.setAppId(appId);
		this.setExpiresIn(expiresIn);
		this.setRoles(roles);
	}

	public ApiSystemLoginTokenRequest(SystemLoginTokenRequest request) {
		this(request.getAppId(), request.getExpiresIn(), request.getRoles());
	}

	@Override
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
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
		return "ApiSystemTokenRequest [appId=" + this.appId + ", expiresIn=" + this.expiresIn + ", roles=" + this.roles
		        + "]";
	}

}
