package com.dereekb.gae.web.api.auth.controller.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API Request for login token authentication.
 *
 * @author dereekb
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiLoginTokenModelContextRequest extends ApiModelRolesRequest {

	/**
	 * Requested expiration time.
	 */
	private Long expirationTime;

	/**
	 * Whether or not to create a login context. Use when only the roles are
	 * requested.
	 */
	private Boolean makeContext;

	/**
	 * Whether or not to include roles in the results.
	 */
	private Boolean includeRoles;

	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Boolean getMakeContext() {
		return this.makeContext;
	}

	public void setMakeContext(Boolean makeContext) {
		this.makeContext = makeContext;
	}

	public Boolean getIncludeRoles() {
		return this.includeRoles;
	}

	public void setIncludeRoles(Boolean includeRoles) {
		this.includeRoles = includeRoles;
	}

	@Override
	public String toString() {
		return "ApiLoginTokenModelContextRequest [atomic=" + this.atomic + ", data=" + this.data + "]";
	}

}
