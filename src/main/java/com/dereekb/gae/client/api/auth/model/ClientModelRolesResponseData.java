package com.dereekb.gae.client.api.auth.model;

import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.auth.controller.model.impl.ApiModelRolesResponseData;

/**
 * Client response data.
 *
 * @see {@link ApiModelRolesResponseData}.
 */
public interface ClientModelRolesResponseData {

	/**
	 * Returns the raw results map.
	 *
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<String, Map<String, Set<String>>> getRawMap();

	/**
	 * Return the results for a specific type.
	 *
	 * @param type
	 *            {@link String}. Never {@code null}.
	 * @return {@link Map}. Never {@code null}.
	 */
	public Map<ModelKey, Set<String>> getRolesForType(String type);

}
