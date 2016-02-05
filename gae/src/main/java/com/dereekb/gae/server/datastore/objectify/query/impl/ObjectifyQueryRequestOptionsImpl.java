package com.dereekb.gae.server.datastore.objectify.query.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link ObjectifyQueryRequestOptions} implementation.
 *
 * @author dereekb
 *
 */
public class ObjectifyQueryRequestOptionsImpl
        implements ObjectifyQueryRequestOptions {

	private static final Integer DEFAULT_LIMIT = 20;

	private Integer limit = DEFAULT_LIMIT;

	private Cursor cursor = null;
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
    public Cursor getCursor() {
		return this.cursor;
	}

	@Override
    public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	@Override
    public Integer getLimit() {
		return this.limit;
	}

	@Override
    public void setLimit(Integer limit) {
		this.limit = limit;
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
	public String toString() {
		return "ObjectifyQueryRequestOptionsImpl [limit=" + this.limit + ", cursor=" + this.cursor + ", allowCache="
		        + this.allowCache + "]";
	}

}
