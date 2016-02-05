package com.dereekb.gae.model.extension.search.query.search.components;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.google.appengine.api.datastore.Cursor;

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
		ObjectifyQueryRequestOptionsImpl options = new ObjectifyQueryRequestOptionsImpl();

		options.setLimit(this.limit);
		options.setAllowCache(this.allowCache);

		if (this.cursor != null) {
			options.setCursor(Cursor.fromWebSafeString(this.cursor));
		}

		query.setOptions(options);
	}

}
