package com.dereekb.gae.server.datastore.objectify.query.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.search.model.impl.SearchOptionsImpl;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link ObjectifyQueryRequestOptions} implementation.
 *
 * @author dereekb
 *
 */
public class ObjectifyQueryRequestOptionsImpl extends SearchOptionsImpl
        implements ObjectifyQueryRequestOptions {

	private boolean allowCache = true;

	public ObjectifyQueryRequestOptionsImpl() {}

	public ObjectifyQueryRequestOptionsImpl(ObjectifyQueryRequestOptions options) {
		if (options != null) {
			this.copyOptions(options);
		}
	}

	public void copyOptions(ObjectifyQueryRequestOptions options) {
		this.setCursor(options.getCursor());
		this.setLimit(options.getLimit());
		this.setAllowCache(options.allowCache());
	}


	@Override
	public boolean allowCache() {
		return this.allowCache;
	}

	@Override
    public void setAllowCache(boolean allowCache) {
		this.allowCache = allowCache;
	}

	@Override
	public Cursor getQueryCursor() {
		Cursor cursor = null;

		if (this.cursor != null) {
			cursor = Cursor.fromWebSafeString(this.cursor);
		}

		return cursor;
	}

	@Override
	public void setQueryCursor(Cursor cursor) {
		if (cursor == null) {
			this.cursor = null;
		} else {
			this.cursor = cursor.toWebSafeString();
		}
	}

	/**
	 *
	public void updateObjectifyQuery(ObjectifyQueryRequestBuilder<?> query) {
		ObjectifyQueryRequestOptionsImpl options = new ObjectifyQueryRequestOptionsImpl();

		options.setLimit(this.limit);
		options.setAllowCache(this.allowCache);

		if (this.cursor != null) {
			options.setCursor(Cursor.fromWebSafeString(this.cursor));
		}

		query.setOptions(options);
	}
	 *
	 */

	@Override
	public String toString() {
		return "ObjectifyQueryRequestOptionsImpl [limit=" + this.limit + ", cursor=" + this.cursor + ", allowCache="
		        + this.allowCache + "]";
	}

}
