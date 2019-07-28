package com.dereekb.gae.client.api.auth.model.roles;

import java.util.List;

import com.dereekb.gae.model.crud.services.request.options.AtomicRequestOptions;
import com.dereekb.gae.web.api.auth.controller.model.roles.impl.ApiModelRolesTypedKeysRequest;

/**
 * Client request for retrieving model roles.
 *
 * @author dereekb
 *
 */
public interface ClientModelRolesRequest
        extends AtomicRequestOptions {

	/**
	 * Returns the list of requested contexts.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ApiModelRolesTypedKeysRequest> getRequestedContexts();

}
