package com.dereekb.gae.client.api.auth.model.context.impl;

import java.util.List;

import com.dereekb.gae.client.api.auth.model.context.ClientModelRolesLoginTokenContextRequest;
import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesRequest;
import com.dereekb.gae.client.api.auth.model.roles.impl.ClientModelRolesRequestImpl;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesTypedKeysRequest;

/**
 * {@link ClientModelRolesLoginTokenContextRequest} implementation.
 *
 * @author dereekb
 * @deprecated
 */
@Deprecated
public class ClientModelRolesLoginTokenContextRequestImpl extends ClientModelRolesRequestImpl
        implements ClientModelRolesLoginTokenContextRequest {

	private Long expirationTime;
	private Boolean makeContext = true;
	private Boolean includeRoles = false;

	public ClientModelRolesLoginTokenContextRequestImpl() {};

	public ClientModelRolesLoginTokenContextRequestImpl(ApiModelRolesTypedKeysRequest requestedContext) {
		super(requestedContext);
	}

	public ClientModelRolesLoginTokenContextRequestImpl(List<ApiModelRolesTypedKeysRequest> requestedContexts) {
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

	// MARK: Convenience
	public void makeRolesOnlyRequest() {
		this.setMakeContext(false);
		this.setIncludeRoles(true);
	}

	@Override
	public String toString() {
		return "ClientModelRolesLoginTokenContextRequestImpl [expirationTime=" + this.expirationTime + ", makeContext="
		        + this.makeContext + ", includeRoles=" + this.includeRoles + ", atomic=" + this.atomic
		        + ", getRequestedContexts()=" + this.getRequestedContexts() + "]";
	}

}
