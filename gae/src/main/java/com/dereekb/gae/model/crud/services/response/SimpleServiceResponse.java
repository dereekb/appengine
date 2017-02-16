package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Simple abstract service response interface that provides info about which
 * models failed.
 * 
 * @author dereekb
 *
 */
public interface SimpleServiceResponse {

	/**
	 * Returns the collection of all {@link ModelKey} values for elements that
	 * failed.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelKey> getFailed();

}
