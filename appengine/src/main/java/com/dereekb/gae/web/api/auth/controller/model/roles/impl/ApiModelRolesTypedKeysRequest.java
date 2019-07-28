package com.dereekb.gae.web.api.auth.controller.model.roles.impl;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * {@link ApiModelRolesRequest} type.
 *
 * @author dereekb
 *
 */
public interface ApiModelRolesTypedKeysRequest
        extends TypedModel {

	/**
	 * Returns the keys set.
	 *
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getKeys();

}
