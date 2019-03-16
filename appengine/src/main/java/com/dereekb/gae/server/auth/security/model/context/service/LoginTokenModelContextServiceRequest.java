package com.dereekb.gae.server.auth.security.model.context.service;

import com.dereekb.gae.model.crud.services.request.options.AtomicRequestOptions;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;

/**
 * {@link LoginTokenModelContextService} request.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContextServiceRequest
        extends AtomicRequestOptions {

	/**
	 * Returns the map of types and keys.
	 * 
	 * @return {@link HashMapWithSet}. Never {@code null}.
	 */
	public HashMapWithSet<String, String> getTypesAndKeys();

}
