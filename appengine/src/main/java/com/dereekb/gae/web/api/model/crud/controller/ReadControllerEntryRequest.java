package com.dereekb.gae.web.api.model.crud.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.request.options.AtomicRequestOptions;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Wraps a read request.
 *
 * @author dereekb
 *
 */
public interface ReadControllerEntryRequest
        extends TypedModel, AtomicRequestOptions {

	/**
	 *
	 * @return {@link List} of {@link ModelKey} values to load.
	 */
	public Collection<ModelKey> getModelKeys();

	/**
	 * Whether or not to load related types.
	 *
	 * @return {@code true} if related types should be loaded.
	 */
	public boolean loadRelatedTypes();

	/**
	 * Optional filter of types to retrieve.
	 *
	 * @return {@link Set} of types.
	 */
	public Set<String> getRelatedTypesFilter();

}
