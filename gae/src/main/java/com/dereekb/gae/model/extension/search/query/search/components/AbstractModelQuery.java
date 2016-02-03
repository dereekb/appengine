package com.dereekb.gae.model.extension.search.query.search.components;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;

/**
 * Base model query that contains a field for an Objectify query cursor and
 * limit.
 *
 * @author dereekb
 *
 */
public abstract class AbstractModelQuery {

	private Integer limit;

	private String cursor;

	private Boolean allowCache;

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getCursor() {
		return this.cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public Boolean getAllowCache() {
		return this.allowCache;
	}

	public void setAllowCache(Boolean allowCache) {
		this.allowCache = allowCache;
	}

	public void updateObjectifyQuery(ObjectifyQueryRequestBuilder<?> query) {

		if (this.limit != null) {
			query.setLimit(query.getLimit());
		}

		if (this.cursor != null) {
			query.setCursor(query.getCursor());
		}

		if (this.allowCache != null) {
			query.setAllowCache(this.allowCache);
		}

	}

}
