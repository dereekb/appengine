package com.dereekb.gae.server.datastore.objectify.query.impl;

import com.dereekb.gae.server.datastore.models.query.impl.IndexedModelQueryRequestOptionsImpl;
import com.dereekb.gae.server.datastore.objectify.query.MutableObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.cursor.impl.ObjectifyCursor;
import com.dereekb.gae.utilities.model.search.request.ParameterSearchOptions;

/**
 * {@link ObjectifyQueryRequestOptions} implementation.
 *
 * @author dereekb
 *
 */
public class ObjectifyQueryRequestOptionsImpl extends IndexedModelQueryRequestOptionsImpl
        implements MutableObjectifyQueryRequestOptions {

	private boolean allowCache = true;

	private boolean allowHybrid = true;

	private Integer chunk;

	public ObjectifyQueryRequestOptionsImpl() {}

	public ObjectifyQueryRequestOptionsImpl(ParameterSearchOptions options) {
		super(options);
	}

	public ObjectifyQueryRequestOptionsImpl(Integer limit) {
		this(null, null, limit);
	}

	public ObjectifyQueryRequestOptionsImpl(Integer offset, Integer limit) {
		this(null, offset, limit);
	}

	public ObjectifyQueryRequestOptionsImpl(String cursor, Integer offset, Integer limit) {
		super(cursor, offset, limit);
	}

	public ObjectifyQueryRequestOptionsImpl(ObjectifyQueryRequestOptions options) {
		if (options != null) {
			this.copyOptions(options);
		}
	}

	public void copyOptions(ObjectifyQueryRequestOptions options) {
		this.setCursor(options.getCursor());
		this.setLimit(options.getLimit());
		this.setOffset(options.getOffset());
		this.setChunk(options.getChunk());
		this.setAllowCache(options.getAllowCache());
	}

	@Override
	public boolean getAllowCache() {
		return this.allowCache;
	}

	@Override
	public void setAllowCache(boolean allowCache) {
		this.allowCache = allowCache;
	}

	@Override
	public boolean getAllowHybrid() {
		return this.allowHybrid;
	}

	@Override
	public void setAllowHybrid(boolean allowHybrid) {
		this.allowHybrid = allowHybrid;
	}

	@Override
	public ObjectifyCursor getObjectifyQueryCursor() {
		return ObjectifyCursor.make(this.getCursor());
	}

	@Override
	public void setQueryCursor(ObjectifyCursor cursor) {
		this.setCursor(cursor);
	}

	@Override
	public Integer getChunk() {
		return this.chunk;
	}

	@Override
	public void setChunk(Integer chunk) {
		this.chunk = chunk;
	}

	@Override
	public String toString() {
		return "ObjectifyQueryRequestOptionsImpl [limit=" + this.getLimit() + ", cursor=" + this.getCursor()
		        + ", allowCache=" + this.allowCache + "]";
	}

}
