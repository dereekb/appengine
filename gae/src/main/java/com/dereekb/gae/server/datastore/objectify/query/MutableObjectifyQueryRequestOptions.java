package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.utilities.model.search.request.MutableSearchOptions;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link ObjectifyQueryRequestOptions} extension that is mutable.
 * 
 * @author dereekb
 *
 */
public interface MutableObjectifyQueryRequestOptions
        extends ObjectifyQueryRequestOptions, MutableSearchOptions {

	public void setAllowCache(boolean cache);

	public void setAllowHybrid(boolean allowHybrid);

	public void setQueryCursor(Cursor cursor);

	public void setChunk(Integer chunk);

}
