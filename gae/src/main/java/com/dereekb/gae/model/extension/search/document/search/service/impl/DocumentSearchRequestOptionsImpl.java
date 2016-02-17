package com.dereekb.gae.model.extension.search.document.search.service.impl;

import com.dereekb.gae.server.search.model.SearchOptions;

/**
 * {@link SearchOptions} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentSearchRequestOptionsImpl
        implements SearchOptions {

	private String cursor;
	private Integer limit;
	private Integer offset;

	public DocumentSearchRequestOptionsImpl() {}

	public DocumentSearchRequestOptionsImpl(String cursor, Integer limit, Integer offset) {
		this.cursor = cursor;
		this.limit = limit;
		this.offset = offset;
	}

	@Override
	public String getCursor() {
		return this.cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	@Override
	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public Integer getOffset() {
		return this.offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "DocumentSearchRequestOptionsImpl [cursor=" + this.cursor + ", limit=" + this.limit + ", offset="
		        + this.offset + "]";
	}

}
