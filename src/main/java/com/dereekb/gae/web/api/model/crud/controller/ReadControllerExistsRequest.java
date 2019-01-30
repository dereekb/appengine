package com.dereekb.gae.web.api.model.crud.controller;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.request.options.AtomicRequestOptions;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ReadControllerEntry} exists requests.
 *
 * @author dereekb
 *
 */
public interface ReadControllerExistsRequest
        extends TypedModel, AtomicRequestOptions {

	/**
	 * @return {@link List} of {@link ModelKey} values to load.
	 */
	public Collection<ModelKey> getModelKeys();

}
