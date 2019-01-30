package com.dereekb.gae.server.datastore.models.query;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link IndexedModelQueryResponse} extension that provides key results.
 *
 * @author dereekb
 */
public interface IndexedModelQueryKeyResponse extends IndexedModelQueryResponse {

	/**
	 * Retrieves {@link ModelKey} values of the models that meet the query
	 * parameters.
	 *
	 * @return {@link List} of keys of matching models.
	 */
	public List<ModelKey> queryModelKeys();

}
