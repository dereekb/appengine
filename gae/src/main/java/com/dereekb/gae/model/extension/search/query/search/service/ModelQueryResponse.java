package com.dereekb.gae.model.extension.search.query.search.service;

import java.util.List;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.googlecode.objectify.Key;

/**
 * Contains {@link ModelKey} query results and model returns.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelQueryResponse<T>
        extends ReadResponse<T> {

	public boolean isKeyOnlyResponse();

	public List<ModelKey> getResponseKeys();

	public List<Key<T>> getResponseObjectifyKeys();

}

