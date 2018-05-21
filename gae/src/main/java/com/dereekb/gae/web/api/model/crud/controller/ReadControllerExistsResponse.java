package com.dereekb.gae.web.api.model.crud.controller;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ReadControllerEntry} exists response.
 *
 * @author dereekb
 *
 */
public interface ReadControllerExistsResponse {

	public Collection<ModelKey> getExists();

	public Collection<ModelKey> getUnavailableModelKeys();

}
