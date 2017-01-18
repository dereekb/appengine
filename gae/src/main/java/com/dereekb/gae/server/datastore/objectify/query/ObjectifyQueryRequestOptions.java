package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.utilities.model.search.request.SearchOptions;
import com.google.appengine.api.datastore.Cursor;

/**
 * Container for Objectify query options.
 *
 * @author dereekb
 * @see {@link ObjectifyQueryRequestBuilder}
 */
public interface ObjectifyQueryRequestOptions
        extends SearchOptions {

	public boolean getAllowCache();

	public Cursor getQueryCursor();

}
