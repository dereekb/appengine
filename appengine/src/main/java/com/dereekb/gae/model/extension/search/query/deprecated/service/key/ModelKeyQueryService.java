package com.dereekb.gae.model.extension.search.query.service.key;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Service for reading {@link ModelKey}s using a custom query.
 *
 * @author dereekb
 */
public interface ModelKeyQueryService<Q> {

	public List<ModelKey> queryKeys(Q query);

}
