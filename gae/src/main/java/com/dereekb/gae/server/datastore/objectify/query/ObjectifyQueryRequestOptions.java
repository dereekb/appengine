package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.utilities.model.search.request.MutableSearchOptions;
import com.google.appengine.api.datastore.Cursor;

/**
 * Container for Objectify query options.
 *
 * @author dereekb
 * @see {@link ObjectifyQueryRequestBuilder}
 */
public interface ObjectifyQueryRequestOptions
        extends MutableSearchOptions {

	public boolean allowCache();

	public void setAllowCache(boolean cache);

	public Cursor getQueryCursor();

	public void setQueryCursor(Cursor cursor);

}
