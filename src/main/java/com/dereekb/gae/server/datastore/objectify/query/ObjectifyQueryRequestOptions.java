package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestOptions;
import com.google.appengine.api.datastore.Cursor;

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

	public Cursor getObjectifyQueryCursor();

	public Integer getChunk();

}
