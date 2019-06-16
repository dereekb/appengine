package com.dereekb.gae.server.datastore.models.query.impl;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestOptions;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;
import com.dereekb.gae.utilities.model.search.request.impl.SearchOptionsImpl;

/**
 * {@link IndexedModelQueryRequestOptions} implementation.
 *
 * @author dereekb
 *
 */
public class IndexedModelQueryRequestOptionsImpl extends SearchOptionsImpl
        implements IndexedModelQueryRequestOptions {

	public IndexedModelQueryRequestOptionsImpl() {
		super();
	}

	public IndexedModelQueryRequestOptionsImpl(ResultsCursor cursor, Integer offset, Integer limit) {
		super(cursor, offset, limit);
	}

	public IndexedModelQueryRequestOptionsImpl(IndexedModelQueryRequestOptions options) {
		super(options);
	}

	public IndexedModelQueryRequestOptionsImpl(SearchOptions options) {
		super(options);
	}

	public IndexedModelQueryRequestOptionsImpl(String cursor, Integer offset, Integer limit) {
		super(cursor, offset, limit);
	}

}
