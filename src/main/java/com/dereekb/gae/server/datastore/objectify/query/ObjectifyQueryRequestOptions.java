package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.cursor.impl.ObjectifyCursor;

/**
 * Container for Objectify query options.
 *
 * @author dereekb
 *
 * @see ObjectifyQueryRequestBuilder
 */
public interface ObjectifyQueryRequestOptions
        extends IndexedModelQueryRequestOptions {

	public boolean getAllowCache();

	public boolean getAllowHybrid();

	public ObjectifyCursor getObjectifyQueryCursor();

	public Integer getChunk();

}
