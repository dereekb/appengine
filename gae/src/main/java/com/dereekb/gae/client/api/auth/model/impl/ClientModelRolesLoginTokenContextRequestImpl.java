package com.dereekb.gae.client.api.auth.model.impl;

import java.util.List;

import com.dereekb.gae.client.api.auth.model.ClientModelRolesLoginTokenContextRequest;
import com.dereekb.gae.client.api.auth.model.ClientModelRolesRequest;
import com.dereekb.gae.web.api.auth.controller.model.ApiLoginTokenModelContextType;

/**
 * {@link ClientModelRolesLoginTokenContextRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ClientModelRolesLoginTokenContextRequestImpl extends ClientModelRolesRequestImpl
        implements ClientModelRolesLoginTokenContextRequest {

	private Long expirationTime;
	private Boolean makeContext;
	private Boolean includeRoles;

	public ClientModelRolesLoginTokenContextRequestImpl(List<ApiLoginTokenModelContextType> requestedContexts) {
		super(requestedContexts);
	}

	public ClientModelRolesLoginTokenContextRequestImpl(ClientModelRolesRequest request) {
		super(request);
	}

	// MARK: ClientModelRolesLoginTokenContextRequest
	@Override
	public Long getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Long expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public Boolean getMakeContext() {
		return this.makeContext;
	}

	public void setMakeContext(Boolean makeContext) {
		this.makeContext = makeContext;
	}

	@Override
	public Boolean getIncludeRoles() {
		return this.includeRoles;
	}

	public void setIncludeRoles(Boolean includeRoles) {
		this.includeRoles = includeRoles;
	}

	@Override
	public String toString() {
		return "ClientModelRolesLoginTokenContextRequestImpl [expirationTime=" + this.expirationTime + ", makeContext="
		        + this.makeContext + ", includeRoles=" + this.includeRoles + ", atomic=" + this.atomic
		        + ", getRequestedContexts()=" + this.getRequestedContexts() + "]";
	}

}
