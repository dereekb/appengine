package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.server.datastore.models.query.MutableIndexedModelQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.cursor.impl.ObjectifyCursor;

/**
 * Mutable {@link ObjectifyQueryRequestOptions} extension.
 *
 * @author dereekb
 *
 */
public interface MutableObjectifyQueryRequestOptions
        extends ObjectifyQueryRequestOptions, MutableIndexedModelQueryRequestOptions {

	public void setAllowCache(boolean cache);

	public void setAllowHybrid(boolean allowHybrid);

	public void setQueryCursor(ObjectifyCursor cursor);

	public void setChunk(Integer chunk);

}
