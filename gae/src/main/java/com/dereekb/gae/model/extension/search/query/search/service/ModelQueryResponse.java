package com.dereekb.gae.model.extension.search.query.search.service;

import java.util.Collection;
import java.util.List;

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
public interface ModelQueryResponse<T> {

	public boolean isKeyOnlyResponse();

	public Collection<T> getModels();

	public List<ModelKey> getResponseKeys();

	public List<Key<T>> getResponseObjectifyKeys();

}

