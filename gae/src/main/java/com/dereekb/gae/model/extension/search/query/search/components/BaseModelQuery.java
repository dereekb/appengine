package com.dereekb.gae.model.extension.search.query.search.components;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;

/**
 * Base model query that contains a field for an Objectify query cursor and
 * limit.
 *
 * @author dereekb
 *
 */
public abstract class BaseModelQuery {

	private Integer limit;

	private String cursor;

	private Boolean disabledCache;

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

	public Boolean getDisabledCache() {
		return this.disabledCache;
	}

	public void setDisabledCache(Boolean disabledCache) {
		this.disabledCache = disabledCache;
	}

	public void updateObjectifyQuery(ObjectifyQuery<?> query) {

		if (this.limit != null) {
			query.setLimit(query.getLimit());
		}

		if (this.cursor != null) {
			query.setCursor(query.getCursor());
		}

		if (this.disabledCache != null) {
			query.setDisableCache(this.disabledCache);
		}

	}

}
