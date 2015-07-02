package com.dereekb.gae.model.extension.search.query.search.service.model;

import java.util.List;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Contains {@link ModelKey} query results and model returns.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ModelQueryResponse<T>
        extends ReadResponse<T> {

	public List<ModelKey> getQueryKeyResults();

}

