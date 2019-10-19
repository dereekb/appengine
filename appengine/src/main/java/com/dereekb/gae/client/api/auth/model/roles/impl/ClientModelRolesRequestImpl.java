package com.dereekb.gae.client.api.auth.model.roles.impl;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesRequest;
import com.dereekb.gae.model.crud.services.request.options.impl.AtomicRequestOptionsImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesTypedKeysRequest;

/**
 * {@link ClientModelRolesRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ClientModelRolesRequestImpl extends AtomicRequestOptionsImpl
        implements ClientModelRolesRequest {

	private List<ApiModelRolesTypedKeysRequest> requestedContexts;

	public ClientModelRolesRequestImpl() {
		super();
		this.requestedContexts = Collections.emptyList();
	}

	public ClientModelRolesRequestImpl(ApiModelRolesTypedKeysRequest requestedContexts) {
		this.setRequestedContexts(requestedContexts);
	}

	public ClientModelRolesRequestImpl(List<ApiModelRolesTypedKeysRequest> requestedContexts) {
		this.setRequestedContexts(requestedContexts);
	}

	public ClientModelRolesRequestImpl(ClientModelRolesRequest request) {
		this(request.getRequestedContexts());
		this.setAtomic(request.isAtomic());
	}

	// MARK: ClientModelRolesRequest
	@Override
	public List<ApiModelRolesTypedKeysRequest> getRequestedContexts() {
		return this.requestedContexts;
	}

	public void setRequestedContexts(ApiModelRolesTypedKeysRequest requestedContext) {
		this.setRequestedContexts(ListUtility.wrap(requestedContext));
	}

	public void setRequestedContexts(List<ApiModelRolesTypedKeysRequest> requestedContexts) {
		if (requestedContexts == null) {
			throw new IllegalArgumentException("requestedContexts cannot be null.");
		}

		this.requestedContexts = requestedContexts;
	}

	@Override
	public String toString() {
		return "ClientModelRolesRequestImpl [requestedContexts=" + this.requestedContexts + "]";
	}

}
