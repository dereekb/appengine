package com.dereekb.gae.server.datastore.models.query.impl;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryKeyResponse;

/**
 * Abstract {@link IndexedModelQueryKeyResponse} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractIndexedModelQueryKeyResponse
        implements IndexedModelQueryKeyResponse {

	@Override
	public boolean hasResults() {
		return this.getResultCount() > 0;
	}

}
