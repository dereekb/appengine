package com.dereekb.gae.server.datastore.objectify.query;

import com.google.appengine.api.datastore.Cursor;

/**
 * Container for Objectify query options.
 *
 * @author dereekb
 * @see {@link ObjectifyQueryRequestBuilder}
 */
public interface ObjectifyQueryRequestOptions {

	public boolean allowCache();

	public void setAllowCache(boolean cache);

	public Integer getLimit();

	public void setLimit(Integer limit);

	public Cursor getCursor();

	public void setCursor(Cursor cursor);

}
