package com.dereekb.gae.model.extension.search.query.service.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.search.query.service.ModelQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.cursor.impl.ObjectifyCursor;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;
import com.dereekb.gae.utilities.model.search.request.impl.SearchRequestImpl;

/**
 * {@link ModelQueryRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ModelQueryRequestImpl extends SearchRequestImpl
        implements ModelQueryRequest {

	private boolean allowCache;
	private boolean allowHybrid;

	private ObjectifyCursor queryCursor;

	public ModelQueryRequestImpl() {
		super();
	}

	public ModelQueryRequestImpl(boolean keysOnly, Map<String, String> searchParameters, SearchOptions options) {
		super(keysOnly, searchParameters, options);
	}

	public ModelQueryRequestImpl(boolean keysOnly, Map<String, String> searchParameters)
	        throws IllegalArgumentException {
		super(keysOnly, searchParameters);
	}

	public ModelQueryRequestImpl(boolean keysOnly) {
		super(keysOnly);
	}

	public ModelQueryRequestImpl(SearchRequest request) throws IllegalArgumentException {
		super(request);
	}

	public ModelQueryRequestImpl(ModelQueryRequest request) throws IllegalArgumentException {
		super(request);
		this.setAllowCache(request.getAllowCache());
		this.setQueryCursor(request.getObjectifyQueryCursor());
	}

	@Override
	public boolean getAllowCache() {
		return this.allowCache;
	}

	public void setAllowCache(boolean allowCache) {
		this.allowCache = allowCache;
	}

	@Override
	public boolean getAllowHybrid() {
		return this.allowHybrid;
	}

	public void setAllowHybrid(boolean allowHybrid) {
		this.allowHybrid = allowHybrid;
	}

	@Override
	public ObjectifyCursor getObjectifyQueryCursor() {
		return this.queryCursor;
	}

	public void setQueryCursor(ObjectifyCursor queryCursor) {
		this.queryCursor = queryCursor;
	}

	@Override
	public Integer getChunk() {
		return null; // Chunk is not accessible from this type.
	}

}
